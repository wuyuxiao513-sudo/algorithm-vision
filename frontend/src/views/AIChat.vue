<template>
  <div class="ai-chat-container">
    <!-- 可拖动的悬浮球 -->
    <div
      class="chat-floating-ball"
      :style="floatingBallStyle"
      @mousedown="startDrag"
      @touchstart="startDrag"
      @click="toggleChatWindow"
    >
      <div class="ball-icon">💬</div>
    </div>

    <!-- 对话弹窗 -->
    <transition name="chat-window">
      <div v-if="showChatWindow" class="chat-window" :style="chatWindowStyle">
        <div class="chat-header">
          <h3>AI 助手</h3>
          <button class="close-button" @click="toggleChatWindow">×</button>
        </div>
        <div class="chat-messages">
          <div
            v-for="(message, index) in messages"
            :key="index"
            class="message"
            :class="message.sender"
          >
            <div
              v-if="message.sender === 'ai' && message.editorHtml"
              class="message-content"
              v-html="message.editorHtml"
            ></div>
            <div v-else class="message-content">{{ message.content }}</div>
            <div class="message-time">{{ formatTime(message.time) }}</div>
          </div>
        </div>
        <div class="chat-input">
          <textarea
            v-model="inputMessage"
            placeholder="输入您的问题..."
            @keydown.enter.ctrl="sendMessage"
          ></textarea>
          <button
            class="send-button"
            @click="sendMessage"
            :disabled="!inputMessage.trim()"
          >
            发送
          </button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted, watch } from "vue";
import { ElMessage } from "element-plus";
import { sendChatMessageStream, generateMemoryId } from "../api/aichat.js";
import MarkdownIt from "markdown-it";

// 初始化markdown-it实例，与Analyse.vue保持一致的配置
const md = new MarkdownIt({
  html: true, // 允许在Markdown中使用HTML标签
  xhtmlOut: true, // 使用自闭合标签（符合XHTML标准）
  breaks: true, // 将换行符转换为<br>标签
  langPrefix: "language-", // 为代码块的class添加前缀
  linkify: true, // 自动转换URL为链接
  typographer: true, // 启用一些语言中立的替换和引号美化
  quotes: '""\'"', // 设置引号样式
  maxNesting: 100, // 最大嵌套深度，防止过深嵌套导致的性能问题
});

// 配置代码高亮处理
md.options.highlight = function (str, lang) {
  if (lang && lang.trim()) {
    return `<pre class="${
      md.options.langPrefix
    }${lang.trim()}"><code>${md.utils.escapeHtml(str)}</code></pre>`;
  }
  return `<pre><code>${md.utils.escapeHtml(str)}</code></pre>`;
};

// 使用markdown-it库将markdown转换为HTML
function parseMarkdownToHtml(markdown) {
  if (!markdown) return "";

  try {
    // 更全面的预处理逻辑，确保输入安全和格式一致
    let normalizedMarkdown = String(markdown); // 确保是字符串

    // 1. 处理各种换行符格式（\r\n, \r, \n）
    normalizedMarkdown = normalizedMarkdown.replace(/\r\n/g, "\n").replace(/\r/g, "\n");

    // 2. 处理多余的空白字符，保留Markdown结构
    normalizedMarkdown = normalizedMarkdown.trim();

    // 3. 处理特殊情况：确保代码块格式正确
    // 修复可能缺少结束标记的代码块
    const codeBlockStartCount = (normalizedMarkdown.match(/```/g) || []).length;
    if (codeBlockStartCount % 2 !== 0) {
      normalizedMarkdown += "\n```"; // 添加缺失的结束标记
    }

    // 4. 使用markdown-it进行解析
    const html = md.render(normalizedMarkdown);

    // 5. 进行后处理，确保HTML结构完整
    let finalHtml = html;

    // 确保HTML结构完整，添加根容器
    return `<div class="markdown-body">${finalHtml}</div>`;
  } catch (error) {
    console.error("Markdown解析错误:", error);
    // 增强的错误处理，提供更安全的回退
    try {
      // 分步骤降级处理：先尝试最小化处理
      const safeText = String(markdown).trim();
      return `<div class="markdown-body"><p>${escapeHtml(safeText)}</p></div>`;
    } catch (fallbackError) {
      // 终极安全回退
      return '<div class="markdown-body"><p>内容解析失败，请检查Markdown格式</p></div>';
    }
  }
}

// HTML转义函数，防止XSS和解析错误
function escapeHtml(text) {
  if (!text) return "";

  const map = {
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#039;",
  };

  return text.toString().replace(/[&<>"']/g, function (m) {
    return map[m];
  });
}

// 悬浮球状态
const floatingBallPosition = reactive({
  x: 20, // 默认左下方x坐标
  y: 80, // 默认左下方y坐标（从底部算起的距离）
});

// 会话相关状态
const memoryId = ref(generateMemoryId()); // 生成会话ID
const isDragging = ref(false);
const showChatWindow = ref(false);
const adsorptionThreshold = 50; // 距离边缘多少像素时自动吸附

// 对话相关状态
const messages = ref([]);
const inputMessage = ref("");
const isLoading = ref(false);

// 计算悬浮球样式
const floatingBallStyle = ref({
  left: `${floatingBallPosition.x}px`,
  bottom: `${floatingBallPosition.y}px`,
});

// 计算对话窗口样式
const chatWindowStyle = ref({
  left:
    floatingBallPosition.x > window.innerWidth / 2
      ? `${floatingBallPosition.x - 380}px`
      : `${floatingBallPosition.x + 70}px`,
  bottom: `${floatingBallPosition.y}px`,
});

// 开始拖拽
function startDrag(event) {
  event.stopPropagation(); // 阻止事件冒泡，避免触发点击事件
  isDragging.value = true;

  const startX = event.clientX || event.touches[0].clientX;
  const startY = event.clientY || event.touches[0].clientY;

  const startPosition = {
    x: floatingBallPosition.x,
    y: floatingBallPosition.y,
  };

  // 移动事件处理函数
  const handleMove = (moveEvent) => {
    if (!isDragging.value) return;

    const currentX = moveEvent.clientX || moveEvent.touches[0].clientX;
    const currentY = moveEvent.clientY || moveEvent.touches[0].clientY;

    // 计算新位置
    let newX = startPosition.x + (currentX - startX);
    let newY = startPosition.y - (currentY - startY); // y轴是从底部算起，所以是减号

    // 限制在可视区域内
    const ballSize = 60; // 悬浮球大小
    newX = Math.max(0, Math.min(window.innerWidth - ballSize, newX));
    newY = Math.max(20, Math.min(window.innerHeight - ballSize, newY));

    // 更新位置
    floatingBallPosition.x = newX;
    floatingBallPosition.y = newY;
    updateFloatingBallStyle();
  };

  // 结束拖拽事件处理函数
  const handleEnd = () => {
    if (!isDragging.value) return;
    isDragging.value = false;

    // 自动吸附到左右边缘
    const ballSize = 60;
    const leftThreshold = 20; // 左侧吸附位置
    const rightThreshold = window.innerWidth - ballSize - 20; // 右侧吸附位置

    // 如果靠近左侧
    if (floatingBallPosition.x < window.innerWidth / 2) {
      floatingBallPosition.x = leftThreshold; // 吸附到左边
    } else {
      floatingBallPosition.x = rightThreshold; // 吸附到右边
    }

    // 添加平滑动画效果
    const ballElement = document.querySelector(".chat-floating-ball");
    if (ballElement) {
      ballElement.style.transition = "left 0.3s ease-out";
      // 重置过渡效果，避免影响拖拽时的即时响应
      setTimeout(() => {
        if (ballElement) {
          ballElement.style.transition = "all 0.3s ease";
        }
      }, 300);
    }

    updateFloatingBallStyle();

    // 移除事件监听器
    document.removeEventListener("mousemove", handleMove);
    document.removeEventListener("touchmove", handleMove, { passive: false });
    document.removeEventListener("mouseup", handleEnd);
    document.removeEventListener("touchend", handleEnd);
  };

  // 添加事件监听器
  document.addEventListener("mousemove", handleMove);
  document.addEventListener("touchmove", handleMove, { passive: false });
  document.addEventListener("mouseup", handleEnd);
  document.addEventListener("touchend", handleEnd);
}

// 更新悬浮球样式
function updateFloatingBallStyle() {
  floatingBallStyle.value = {
    left: `${floatingBallPosition.x}px`,
    bottom: `${floatingBallPosition.y}px`,
  };

  // 同步更新对话窗口位置
  chatWindowStyle.value = {
    left:
      floatingBallPosition.x > window.innerWidth / 2
        ? `${floatingBallPosition.x - 380}px`
        : `${floatingBallPosition.x + 70}px`,
    bottom: `${floatingBallPosition.y}px`,
  };
}

// 切换对话窗口显示状态
function toggleChatWindow(event) {
  if (isDragging.value) return; // 如果正在拖拽，不触发点击事件

  showChatWindow.value = !showChatWindow.value;

  // 如果是首次打开对话窗口，添加一条欢迎消息
  if (showChatWindow.value && messages.value.length === 0) {
    const welcomeMessage = "您好！我是AI助手，请问有什么可以帮助您的？";
    messages.value.push({
      sender: "ai",
      content: welcomeMessage,
      editorHtml: parseMarkdownToHtml(welcomeMessage),
      time: new Date(),
    });
  }
}

// 发送消息
function sendMessage() {
  var message = inputMessage.value.trim();
  if (!message) return;

  // 添加用户消息到消息列表
  messages.value.push({
    id: Date.now() + "-user",
    sender: "user",
    content: message,
    time: new Date().toISOString(),
  });

  // 清空输入框
  inputMessage.value = "";

  // 滚动到底部
  setTimeout(function () {
    var chatMessages = document.querySelector(".chat-messages");
    if (chatMessages) {
      chatMessages.scrollTop = chatMessages.scrollHeight;
    }
  }, 0);

  // 标记为加载中
  isLoading.value = true;

  // 添加AI回复占位符
  var aiMessage = {
    id: Date.now() + "-ai",
    sender: "ai",
    content: "",
    editorHtml: "",
    time: new Date().toISOString(),
  };
  messages.value.push(aiMessage);
  var aiMessageIndex = messages.value.length - 1;

  // 1. 强化换行符处理（新增）
  const removeAllLineBreaks = (str) => {
    // 处理所有可能的换行符：\n \r \r\n 以及Unicode换行符
    return str.replace(/[\n\r\u2028\u2029]+/g, "").trim();
  };

  // 2. 修改你的处理函数
  let fullResponse = "";
  let displayedLength = 0;
  let typeWriterTimeout = null;

  function typeWriter() {
    if (displayedLength < fullResponse.length) {
      const nextChar = fullResponse.charAt(displayedLength);
      messages.value[aiMessageIndex].content += nextChar;

      // 3. 调整Markdown解析（关键修改）
      try {
        // 先移除内容中的所有换行，再解析Markdown
        const contentWithoutBreaks = removeAllLineBreaks(
          messages.value[aiMessageIndex].content
        );
        messages.value[aiMessageIndex].editorHtml = parseMarkdownToHtml(
          contentWithoutBreaks
        );
      } catch (e) {
        messages.value[aiMessageIndex].editorHtml =
          '<div class="markdown-body"><p>' +
          escapeHtml(messages.value[aiMessageIndex].content) +
          "</p></div>";
      }

      // 滚动逻辑保持不变
      setTimeout(() => {
        const chatDiv = document.querySelector(".chat-messages");
        if (chatDiv) chatDiv.scrollTop = chatDiv.scrollHeight;
      }, 0);

      displayedLength++;
      typeWriterTimeout = setTimeout(typeWriter, 30);
    }
  }

  // 4. 处理流式数据时也应用强化处理
  sendChatMessageStream(
    { memoryId: memoryId.value, message: message },
    function (chunk, accumulatedData) {
      let processedChunk = chunk;
      if (chunk && chunk.indexOf("data:") === 0) {
        processedChunk = chunk
          .replace(/^data:/, "")
          // 使用强化的换行处理函数
          .replace(/[\n\r\u2028\u2029]+/g, "")
          .trim();
      }

      if (processedChunk) {
        fullResponse += processedChunk;
        if (!typeWriterTimeout) {
          typeWriter();
        }
      }
    },
    function () {
      // 流结束回调
      isLoading.value = false;
      displayedLength = 0; // 重置状态
      clearTimeout(typeWriterTimeout); // 清除定时器
      typeWriterTimeout = null;
    },
    function (error) {
      // 错误处理...
      isLoading.value = false;
      displayedLength = 0;
      clearTimeout(typeWriterTimeout);
      typeWriterTimeout = null;
    }
  );
}

// 格式化时间
function formatTime(date) {
  const now = new Date(date);
  const hours = now.getHours().toString().padStart(2, "0");
  const minutes = now.getMinutes().toString().padStart(2, "0");
  return `${hours}:${minutes}`;
}

// 监听窗口大小变化，更新悬浮球位置
function handleResize() {
  // 确保悬浮球不会超出窗口边界
  const ballSize = 60;
  floatingBallPosition.x = Math.min(
    floatingBallPosition.x,
    window.innerWidth - ballSize - 20
  );
  updateFloatingBallStyle();
}

// 监听聊天窗口显示状态，滚动到底部
watch(showChatWindow, (newVal) => {
  if (newVal) {
    setTimeout(() => {
      const chatMessages = document.querySelector(".chat-messages");
      if (chatMessages) {
        chatMessages.scrollTop = chatMessages.scrollHeight;
      }
    }, 0);
  }
});

// 组件挂载时添加事件监听
onMounted(() => {
  // 这里可以添加从localStorage恢复之前的会话ID的逻辑
  // 例如：const savedMemoryId = localStorage.getItem('aiChatMemoryId');
  // if (savedMemoryId) memoryId.value = parseInt(savedMemoryId);
  window.addEventListener("resize", handleResize);
});

// 组件卸载时移除事件监听
onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
});

// 监听聊天窗口显示状态，滚动到底部
watch(showChatWindow, (newVal) => {
  if (newVal) {
    setTimeout(() => {
      const chatMessages = document.querySelector(".chat-messages");
      if (chatMessages) {
        chatMessages.scrollTop = chatMessages.scrollHeight;
      }
    }, 0);
  }
});
</script>

<style lang="less" scoped>
.ai-chat-container {
  position: relative;
  width: 100%;
  height: 100%;
}

/* 悬浮球样式 */
.chat-floating-ball {
  position: fixed;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: linear-gradient(135deg, #409eff, #667eea);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
  cursor: pointer;
  transition: all 0.3s ease;
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: center;
  user-select: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;

  &:hover {
    transform: scale(1.1);
    box-shadow: 0 6px 16px rgba(64, 158, 255, 0.6);
  }

  &:active {
    transform: scale(0.95);
  }

  .ball-icon {
    font-size: 28px;
    animation: bounce 2s infinite;
  }
}

/* 对话窗口样式 */
.chat-window {
  position: fixed;
  width: 360px;
  max-height: 500px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  z-index: 1001;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.chat-header {
  background: #f5f7fa;
  padding: 12px 16px;
  border-bottom: 1px solid #ebeef5;
  display: flex;
  align-items: center;
  justify-content: space-between;

  h3 {
    margin: 0;
    font-size: 16px;
    font-weight: 500;
    color: #303133;
  }

  .close-button {
    width: 24px;
    height: 24px;
    border: none;
    background: none;
    font-size: 20px;
    color: #909399;
    cursor: pointer;
    border-radius: 4px;
    display: flex;
    align-items: center;
    justify-content: center;

    &:hover {
      background: #ecf5ff;
      color: #409eff;
    }
  }
}

.chat-messages {
  flex: 1;
  padding: 16px;
  overflow-y: auto;
  max-height: 350px;
  background: #ffffff;
}

.message {
  margin-bottom: 16px;

  &.user {
    display: flex;
    flex-direction: column;
    align-items: flex-end;

    .message-content {
      background: #409eff;
      color: white;
      border-radius: 10px 10px 0 10px;
    }
  }

  &.ai {
    display: flex;
    flex-direction: column;
    align-items: flex-start;

    .message-content {
      background: #f5f7fa;
      color: #606266;
      border-radius: 10px 10px 10px 0;
    }
  }

  .message-content {
    max-width: 80%;
    padding: 8px 12px;
    word-wrap: break-word;
    word-break: break-word; /* 增强换行效果 */
    white-space: normal; /* 确保文本正常换行 */
    line-height: 1.5;
    overflow-wrap: break-word; /* 防止长单词溢出 */
  }

  .message-time {
    font-size: 12px;
    color: #909399;
    margin-top: 4px;
  }
}

.chat-input {
  padding: 12px;
  border-top: 1px solid #ebeef5;
  background: #fafafa;
  display: flex;
  gap: 8px;

  textarea {
    flex: 1;
    border: 1px solid #dcdfe6;
    border-radius: 6px;
    padding: 8px 12px;
    resize: none;
    height: 60px;
    font-size: 14px;
    line-height: 1.5;

    &:focus {
      outline: none;
      border-color: #409eff;
    }
  }

  .send-button {
    width: 60px;
    height: 60px;
    border: none;
    background: #409eff;
    color: white;
    border-radius: 6px;
    cursor: pointer;
    font-size: 14px;
    transition: background 0.3s;

    &:hover:not(:disabled) {
      background: #66b1ff;
    }

    &:disabled {
      background: #c0c4cc;
      cursor: not-allowed;
    }
  }
}

/* 动画效果 */
@keyframes bounce {
  0%,
  20%,
  50%,
  80%,
  100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-5px);
  }
  60% {
    transform: translateY(-3px);
  }
}

/* 对话窗口过渡动画 */
.chat-window-enter-active,
.chat-window-leave-active {
  transition: all 0.3s ease;
}

.chat-window-enter-from,
.chat-window-leave-to {
  opacity: 0;
  transform: scale(0.9);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .chat-window {
    width: 90vw;
    max-width: none;
    left: 5vw !important;
    right: 5vw !important;
  }

  .chat-floating-ball {
    width: 50px;
    height: 50px;

    .ball-icon {
      font-size: 24px;
    }
  }
}
</style>
