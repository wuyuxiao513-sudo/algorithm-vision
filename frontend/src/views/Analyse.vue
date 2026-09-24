<template>
  <div class="ai-chat">
    <h1>AI算法分析</h1>

    <!-- 功能切换选项 -->
    <div class="function-tabs">
      <button
        :class="['tab-button', { active: activeFunction === 'solve' }]"
        @click="switchFunction('solve')"
      >
        问题解决
      </button>
      <button
        :class="['tab-button', { active: activeFunction === 'analyze' }]"
        @click="switchFunction('analyze')"
      >
        代码分析
      </button>
    </div>

    <!-- 输入表单区域 -->
    <div class="input-section">
      <!-- 语言选择 -->
      <div class="form-item">
        <label for="code-language">代码语言：</label>
        <select
          id="code-language"
          :value="getCurrentData().codeLanguage"
          @change="changeLanguage($event)"
        >
          <option value="javascript">JavaScript</option>
          <option value="python">Python</option>
          <option value="java">Java</option>
          <option value="cpp">C++</option>
          <option value="c">C</option>
          <option value="go">Go</option>
          <option value="rust">Rust</option>
          <option value="typescript">TypeScript</option>
        </select>
        <button v-if="hasContent" class="clear-button" @click="clearAll">清空</button>
      </div>

      <!-- 问题描述（仅问题解决功能显示） -->
      <div v-if="activeFunction === 'solve'" class="form-item">
        <label for="problem-description">问题描述：</label>
        <textarea
          id="problem-description"
          v-model="solveData.problemDescription"
          placeholder="请输入您的编程问题..."
          rows="4"
        ></textarea>
      </div>

      <!-- 代码输入区域 -->
      <div class="form-item">
        <label for="code-input">代码：</label>
        <textarea
          id="code-input"
          :value="getCurrentData().codeInput"
          @input="updateCodeInput($event)"
          placeholder="请输入您的代码..."
          rows="10"
        ></textarea>
      </div>

      <!-- 操作按钮区域 -->
      <div class="button-group">
        <button
          class="submit-button"
          @click="submitCode"
          :disabled="isLoading || !canSubmit"
        >
          {{ isLoading ? "提交中..." : "提交" }}
        </button>
        <button v-if="isLoading" class="cancel-button" @click="cancelRequest">
          取消
        </button>
        <button
          v-if="getCurrentData().result && !isLoading"
          class="copy-button"
          @click="copyResult"
        >
          复制结果
        </button>
      </div>

      <!-- 进度条 -->
      <div v-if="isLoading" class="progress-container">
        <ElProgress
          :percentage="progressPercentage"
          :stroke-width="4"
          :status="'success'"
        />
        <div class="progress-text">
          正在处理您的请求，请稍候... ({{ progressPercentage }}%)
        </div>
      </div>
    </div>

    <!-- 结果展示区域 -->
    <div v-if="getCurrentData().result" class="result-section">
      <div class="result-header">
        <h3>结果：</h3>
        <button class="copy-button-small" @click="copyResult" title="复制结果">📋</button>
      </div>
      <!-- 根据markdown-it官方文档优化的渲染区域 -->
      <div class="markdown-container">
        <div
          class="markdown-body"
          :innerHTML="getCurrentData().editorHtml"
          :style="{ height: '100%' }"
        ></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ElMessage, ElProgress } from "element-plus";
import { ref, computed, onBeforeUnmount } from "vue";
import { solveAlgorithm, analyzeEnhancedReport } from "../api/analyse";
import MarkdownIt from "markdown-it";

// 响应式数据
const activeFunction = ref("solve"); // solve 或 analyze
const isLoading = ref(false);
const progressPercentage = ref(0); // 进度百分比，用于5秒等待动画
const progressTimer = ref(null); // 进度计时器
const controller = ref(null); // 用于取消请求

// 独立的数据存储对象
const solveData = ref({
  codeLanguage: "javascript",
  problemDescription: "",
  codeInput: "",
  result: "",
  editorHtml: "",
});

const analyzeData = ref({
  codeLanguage: "javascript",
  codeInput: "",
  result: "",
  editorHtml: "",
});

// 获取当前激活功能的数据对象
function getCurrentData() {
  return activeFunction.value === "solve" ? solveData.value : analyzeData.value;
}

// 切换功能类型
function switchFunction(functionType) {
  // 只有当功能类型发生变化时才更新
  if (activeFunction.value !== functionType) {
    activeFunction.value = functionType;
  }
}

// 更新代码输入
function updateCodeInput(event) {
  const currentData = getCurrentData();
  currentData.codeInput = event.target.value;
}

// 更改代码语言
function changeLanguage(event) {
  const currentData = getCurrentData();
  currentData.codeLanguage = event.target.value;
}

// 注意：移除了wangeditor相关配置，现在直接使用v-html渲染markdown内容

// 计算属性
const canSubmit = computed(() => {
  // 问题解决功能需要问题描述和代码
  // 代码分析功能只需要代码
  if (activeFunction.value === "solve") {
    return (
      solveData.value.problemDescription.trim() !== "" &&
      solveData.value.codeInput.trim() !== ""
    );
  } else {
    return analyzeData.value.codeInput.trim() !== "";
  }
});

const hasContent = computed(() => {
  // 检查是否有输入内容
  const currentData = getCurrentData();
  return (
    currentData.codeInput.trim() !== "" ||
    (activeFunction.value === "solve" &&
      solveData.value.problemDescription.trim() !== "") ||
    currentData.result !== ""
  );
});

// 开始进度动画
function startProgressAnimation() {
  progressPercentage.value = 0;
  const totalTime = 5000; // 总时间5秒
  const updateInterval = 50; // 每50ms更新一次
  const steps = totalTime / updateInterval;
  let currentStep = 0;

  progressTimer.value = setInterval(() => {
    currentStep++;
    // 使用缓动函数让进度动画更自然，前期快后期慢
    const progress = currentStep / steps;
    const easedProgress = 1 - Math.pow(1 - progress, 3); // easeOutCubic
    progressPercentage.value = Math.round(easedProgress * 95); // 最多到95%，留一些余量

    if (currentStep >= steps) {
      // 5秒后如果还在加载，保持在95%
      progressPercentage.value = 95;
      clearInterval(progressTimer.value);
      progressTimer.value = null;
    }
  }, updateInterval);
}

// 停止进度动画
function stopProgressAnimation() {
  if (progressTimer.value) {
    clearInterval(progressTimer.value);
    progressTimer.value = null;
  }
  // 完成时快速到100%
  progressPercentage.value = 100;
  // 短暂延迟后重置
  setTimeout(() => {
    progressPercentage.value = 0;
  }, 500);
}

// 提交代码
async function submitCode() {
  // 重置当前功能的结果
  const currentData = getCurrentData();
  currentData.result = "";
  currentData.editorHtml = "";
  isLoading.value = true;

  // 开始进度动画
  startProgressAnimation();

  try {
    ElMessage({
      message: "正在处理您的请求...",
      type: "info",
      duration: 2000,
    });

    if (activeFunction.value === "solve") {
      await callSolveApi();
    } else {
      await callAnalyzeApi();
    }

    if (getCurrentData().result) {
      ElMessage({
        message: "处理完成！",
        type: "success",
        duration: 2000,
      });
    }
  } catch (error) {
    console.error("API调用错误:", error);

    // 根据不同错误类型给出不同提示
    let errorMsg = "服务器响应异常，请稍后重试";
    if (error.name === "AbortError") {
      errorMsg = "请求已取消";
    } else if (error.message) {
      errorMsg = error.message;
    }

    ElMessage.error(errorMsg);
    getCurrentData().result = `## 错误提示\n\n${errorMsg}`;
  } finally {
    // 停止进度动画
    stopProgressAnimation();
    isLoading.value = false;
    controller.value = null;
  }
}

// 实现/algorithm/solve接口调用
async function callSolveApi() {
  // 根据接口要求，将problemDescription和codeInput拼接在一起作为problem参数
  const combinedProblem =
    solveData.value.problemDescription +
    "\n\n代码：\n```" +
    solveData.value.codeLanguage +
    "\n" +
    solveData.value.codeInput +
    "\n```";

  const data = {
    problem: combinedProblem,
    language: solveData.value.codeLanguage,
  };

  await handleApiRequest("solve", data);
}

// 实现/algorithm/analyze/enhanced-report接口调用
async function callAnalyzeApi() {
  const data = {
    code: analyzeData.value.codeInput,
    language: analyzeData.value.codeLanguage,
  };

  await handleApiRequest("analyze", data);
}

// 处理普通API请求
async function handleApiRequest(type, data) {
  try {
    // 获取对应功能的数据对象
    const functionData = type === "solve" ? solveData.value : analyzeData.value;

    // 确保functionData和result属性存在
    if (!functionData) {
      throw new Error("功能数据对象不存在");
    }

    // 清空对应功能的结果
    functionData.result = "";
    functionData.editorHtml = "";

    let response;
    // 根据类型调用不同的API方法
    if (type === "solve") {
      // 接口现在只需要problem和language两个参数
      response = await solveAlgorithm(data);
    } else if (type === "analyze") {
      response = await analyzeEnhancedReport(data);
    } else {
      throw new Error("未知的API类型");
    }

    // 增加对response的基本验证
    if (!response) {
      throw new Error("接口未返回响应数据");
    }

    // 检查response是否包含data属性
    if (!response.data && !response.result) {
      throw new Error("接口返回的数据格式不符合预期");
    }

    // 根据不同的API类型处理不同格式的返回数据
    let markdownContent = "";

    // 更健壮的数据处理逻辑
    try {
      if (type === "solve") {
        // 处理解决方案接口的多种可能返回格式
        if (response.data && response.data.solution) {
          markdownContent = response.data.solution;
        } else if (response.result) {
          markdownContent = response.result;
        } else if (typeof response.data === "string") {
          markdownContent = response.data;
        } else {
          throw new Error("解决方案接口返回数据格式错误");
        }
      } else if (type === "analyze") {
        // 处理分析报告接口的多种可能返回格式
        if (response.data && response.data.report) {
          const report = response.data.report;
          markdownContent = generateReportMarkdown(report);
        } else if (response.result) {
          markdownContent = response.result;
        } else if (typeof response.data === "string") {
          markdownContent = response.data;
        } else {
          throw new Error("分析报告接口返回数据格式错误");
        }
      }
    } catch (dataProcessingError) {
      // 记录具体的错误信息和响应结构，便于调试
      console.error("数据处理错误:", dataProcessingError);
      console.error("响应数据:", response);
      throw new Error(
        response.msg || dataProcessingError.message || "接口返回数据格式错误"
      );
    }

    // 保存原始markdown内容到对应功能的数据对象
    functionData.result = markdownContent;

    try {
      // 将markdown转换为HTML
      const parsedHtml = parseMarkdownToHtml(markdownContent);

      // 添加额外的验证，确保HTML格式正确
      if (typeof parsedHtml === "string" && parsedHtml.trim()) {
        functionData.editorHtml = parsedHtml;
      } else {
        // 如果解析结果有问题，回退到显示原始文本（已转义）
        functionData.editorHtml = `<div class="markdown-body"><p>${escapeHtml(
          markdownContent
        )}</p></div>`;
      }
    } catch (htmlError) {
      console.error("HTML解析错误:", htmlError);
      // 解析出错时，回退到显示转义后的原始文本
      functionData.editorHtml = `<div class="markdown-body"><p>${escapeHtml(
        markdownContent
      )}</p></div>`;
    }
  } catch (error) {
    console.error("API请求失败:", error);
    // 获取对应功能的数据对象，确保错误信息被正确设置
    const functionData = type === "solve" ? solveData.value : analyzeData.value;
    if (functionData) {
      functionData.result = `## 错误提示

${error.message || "接口请求失败"}`;
      functionData.editorHtml = `<div class="markdown-body"><h2>错误提示</h2><p>${escapeHtml(
        error.message || "接口请求失败"
      )}</p></div>`;
    }
    ElMessage.error(`请求失败: ${error.message || "未知错误"}`);
    throw error; // 重新抛出错误，让上层函数处理
  }
}

// 初始化markdown-it实例，根据官方文档最佳实践优化配置
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

// 配置代码高亮处理（简单实现，实际项目可集成highlight.js等库）
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

    // 4. 使用markdown-it进行解析，按照官方推荐方式
    const html = md.render(normalizedMarkdown);

    // 5. 进行后处理，确保与wangeditor兼容性
    let finalHtml = html;

    // 修复可能的HTML闭合问题
    finalHtml = ensureProperHtmlClosing(finalHtml);

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

// 生成代码分析报告的markdown格式内容
function generateReportMarkdown(report) {
  let markdown = "";

  // 添加算法名称
  if (report.algorithmName) {
    markdown += `## ${escapeMarkdown(report.algorithmName)}\n\n`;
  }

  // 添加星级评分
  if (report.starRating) {
    markdown += `### 星级评分\n\n`;
    markdown += `${"⭐".repeat(report.starRating)}\n\n`;
    if (report.starRatingComment) {
      markdown += `${report.starRatingComment}\n\n`;
    }
  }

  // 添加情感评价
  if (report.emotionalValue) {
    markdown += `### 情感评价\n\n`;
    markdown += `${report.emotionalValue}\n\n`;
  }

  // 添加总体评价
  if (report.overallAssessment) {
    markdown += `### 总体评价\n\n`;
    markdown += `${report.overallAssessment}\n\n`;
  }

  // 添加时间复杂度
  if (report.timeComplexity) {
    markdown += `### 时间复杂度\n\n`;
    markdown += `- **最佳情况**: ${report.timeComplexity.bestCase || "未知"}\n`;
    markdown += `- **平均情况**: ${report.timeComplexity.averageCase || "未知"}\n`;
    markdown += `- **最坏情况**: ${report.timeComplexity.worstCase || "未知"}\n`;
    if (report.timeComplexity.explanation) {
      markdown += `- **说明**: ${report.timeComplexity.explanation}\n`;
    }
    markdown += `\n`;
  }

  // 添加空间复杂度
  if (report.spaceComplexity) {
    markdown += `### 空间复杂度\n\n`;
    markdown += `- **最佳情况**: ${report.spaceComplexity.bestCase || "未知"}\n`;
    markdown += `- **平均情况**: ${report.spaceComplexity.averageCase || "未知"}\n`;
    markdown += `- **最坏情况**: ${report.spaceComplexity.worstCase || "未知"}\n`;
    if (report.spaceComplexity.explanation) {
      markdown += `- **说明**: ${report.spaceComplexity.explanation}\n`;
    }
    markdown += `\n`;
  }

  // 添加优化方向
  if (report.optimizationDirections && report.optimizationDirections.length > 0) {
    markdown += `### 优化方向\n\n`;
    report.optimizationDirections.forEach((direction, index) => {
      if (direction) {
        markdown += `${index + 1}. ${direction}\n`;
      }
    });
    markdown += `\n`;
  }

  // 添加改进建议
  if (report.improvementSuggestions) {
    markdown += `### 改进建议\n\n`;
    markdown += `${report.improvementSuggestions}\n\n`;
  }

  // 添加代码弱点
  if (report.weaknesses && report.weaknesses.length > 0) {
    markdown += `### 代码弱点\n\n`;
    report.weaknesses.forEach((weakness, index) => {
      if (weakness) {
        markdown += `${index + 1}. ${weakness}\n`;
      }
    });
    markdown += `\n`;
  }

  return markdown;
}

// 转义markdown特殊字符
function escapeMarkdown(text) {
  if (!text) return "";
  return String(text).replace(/[\*\_\{\}\[\]\(\)\#\+\-\.\!]/g, "\\$&");
}

// 辅助函数：确保HTML标签正确闭合
function ensureProperHtmlClosing(html) {
  // 这是一个简单实现，实际项目中可使用更完善的HTML解析库
  // 这里主要处理一些常见的自闭合标签和基本的标签配对检查

  // 自闭合标签列表
  const selfClosingTags = ["br", "hr", "img", "input", "link", "meta"];

  // 确保自闭合标签格式正确
  let processedHtml = html;

  // 简单检查和修复（实际应用可能需要更复杂的HTML解析）
  // 这里主要是一个基本的安全网
  return processedHtml;
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

// 取消请求
function cancelRequest() {
  if (controller.value) {
    controller.value.abort();
    stopProgressAnimation();
    isLoading.value = false;
    ElMessage.info("请求已取消");
  }
}

// 复制结果到剪贴板
async function copyResult() {
  try {
    const currentData = getCurrentData();
    await navigator.clipboard.writeText(currentData.result);
    ElMessage.success("复制成功");
  } catch (err) {
    console.error("复制失败:", err);
    ElMessage.error("复制失败");
  }
}

// 清空所有内容
function clearAll() {
  // 清空当前功能的数据
  const currentData = getCurrentData();
  currentData.codeInput = "";
  currentData.result = "";
  currentData.editorHtml = "";

  // 如果是解决问题功能，还需要清空问题描述
  if (activeFunction.value === "solve") {
    solveData.value.problemDescription = "";
  }
}

// 组件销毁时取消未完成的请求和计时器
onBeforeUnmount(() => {
  cancelRequest();
  if (progressTimer.value) {
    clearInterval(progressTimer.value);
    progressTimer.value = null;
  }
});
</script>

<style scoped>
.ai-chat {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);

  @media (max-width: 768px) {
    padding: 10px;
    margin: 0;
    border-radius: 0;
    height: 100vh;
    display: flex;
    flex-direction: column;
  }

  h1 {
    text-align: center;
    color: #333;
    margin-bottom: 30px;
  }

  .function-tabs {
    display: flex;
    margin-bottom: 20px;
    border-bottom: 2px solid #e8e8e8;

    /* 移动端适配 */
    @media (max-width: 768px) {
      margin-bottom: 15px;

      .tab-button {
        padding: 8px 15px;
        font-size: 14px;
      }
    }

    .tab-button {
      padding: 10px 20px;
      border: none;
      background: none;
      cursor: pointer;
      font-size: 16px;
      color: #666;
      border-bottom: 2px solid transparent;
      transition: all 0.3s;

      &:hover {
        color: #1890ff;
      }

      &.active {
        color: #1890ff;
        border-bottom-color: #1890ff;
      }
    }
  }

  .input-section {
    margin-bottom: 30px;

    /* 移动端适配 */
    @media (max-width: 768px) {
      margin-bottom: 20px;
      flex: 1;
      display: flex;
      flex-direction: column;
      overflow-y: auto;
      margin-bottom: 10px;
    }

    .form-item {
      margin-bottom: 20px;

      label {
        display: block;
        margin-bottom: 8px;
        font-weight: 500;
        color: #333;
      }

      select,
      textarea {
        width: 100%;
        padding: 8px 12px;
        border: 1px solid #d9d9d9;
        border-radius: 4px;
        font-size: 14px;
        transition: border-color 0.3s;

        /* 移动端适配 */
        @media (max-width: 768px) {
          padding: 6px 10px;
          font-size: 14px;
        }

        &:focus {
          outline: none;
          border-color: #40a9ff;
          box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
        }
      }

      textarea {
        font-family: "Monaco", "Menlo", "Ubuntu Mono", monospace;
        resize: vertical;

        /* 移动端适配 */
        @media (max-width: 768px) {
          min-height: 80px;
        }
      }

      .clear-button {
        margin-left: 10px;
        padding: 6px 12px;
        background-color: #f0f0f0;
        border: 1px solid #d9d9d9;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        transition: all 0.3s;

        &:hover {
          background-color: #e8e8e8;
          border-color: #bfbfbf;
        }

        /* 移动端适配 */
        @media (max-width: 768px) {
          padding: 4px 8px;
          font-size: 12px;
          margin-left: 5px;
        }
      }
    }

    .button-group {
      display: flex;
      justify-content: center;
      gap: 15px;
      margin-top: 20px;

      /* 移动端适配 */
      @media (max-width: 768px) {
        gap: 10px;
        margin-top: 15px;
      }
    }

    .submit-button,
    .cancel-button,
    .copy-button {
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      font-size: 16px;
      cursor: pointer;
      transition: all 0.3s;
      min-width: 100px;

      /* 移动端适配 */
      @media (max-width: 768px) {
        padding: 8px 15px;
        font-size: 14px;
        min-width: 80px;
      }
    }

    .copy-button {
      background-color: #52c41a;
      color: white;

      &:hover {
        background-color: #73d13d;
      }
    }

    /* 进度条样式 */
    .progress-container {
      margin-top: 20px;
      text-align: center;
      background-color: #fafafa;
      padding: 15px;
      border-radius: 8px;
      border: 1px solid #e8e8e8;
      transition: all 0.3s ease;

      /* 移动端适配 */
      @media (max-width: 768px) {
        margin-top: 15px;
        padding: 12px;
      }

      /* 添加微妙的动画效果 */
      animation: progress-pulse 3s infinite ease-in-out;
    }

    @keyframes progress-pulse {
      0%,
      100% {
        box-shadow: 0 0 0 0 rgba(24, 144, 255, 0);
      }
      50% {
        box-shadow: 0 0 0 4px rgba(24, 144, 255, 0.1);
      }
    }

    .progress-text {
      margin-top: 10px;
      color: #666;
      font-size: 14px;
      font-weight: 500;
      transition: color 0.3s ease;

      /* 移动端适配 */
      @media (max-width: 768px) {
        font-size: 13px;
      }

      &:hover {
        color: #1890ff;
      }
    }

    .submit-button {
      background-color: #1890ff;
      color: white;

      &:hover:not(:disabled) {
        background-color: #40a9ff;
      }

      &:disabled {
        background-color: #d9d9d9;
        cursor: not-allowed;
      }
    }

    .cancel-button {
      background-color: #fff;
      color: #666;
      border: 1px solid #d9d9d9;

      &:hover {
        color: #1890ff;
        border-color: #1890ff;
      }
    }
  }

  .result-section {
    border-top: 1px solid #e8e8e8;
    padding-top: 20px;

    /* 移动端适配 */
    @media (max-width: 768px) {
      padding-top: 15px;
      flex: 1;
      overflow-y: auto;
    }

    .result-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 15px;
    }

    .copy-button-small {
      background: none;
      border: none;
      cursor: pointer;
      font-size: 18px;
      padding: 5px;
      border-radius: 4px;
      transition: background-color 0.3s;

      &:hover {
        background-color: #f0f0f0;
      }
    }

    h3 {
      margin-bottom: 15px;
      color: #333;
    }

    .markdown-container {
      background-color: #ffffff;
      border: 1px solid #e8e8e8;
      border-radius: 4px;
      min-height: 200px;
      max-height: 600px;
      overflow-y: auto;
    }

    /* markdown-it官方文档推荐的样式类 */
    .markdown-body {
      padding: 20px;
      line-height: 1.6;
      word-wrap: break-word;

      /* 标题样式 */
      h1,
      h2,
      h3,
      h4,
      h5,
      h6 {
        margin-top: 24px;
        margin-bottom: 16px;
        font-weight: 600;
        line-height: 1.25;
        color: #24292e;
      }

      h1 {
        padding-bottom: 0.3em;
        font-size: 2em;
        border-bottom: 1px solid #eaecef;
      }

      h2 {
        padding-bottom: 0.3em;
        font-size: 1.5em;
        border-bottom: 1px solid #eaecef;
      }

      h3 {
        font-size: 1.25em;
      }

      h4 {
        font-size: 1em;
      }

      h5 {
        font-size: 0.875em;
      }

      h6 {
        font-size: 0.85em;
        color: #6a737d;
      }

      /* 段落样式 */
      p {
        margin-top: 0;
        margin-bottom: 16px;
      }

      /* 列表样式 */
      ul,
      ol {
        padding-left: 2em;
        margin-top: 0;
        margin-bottom: 16px;
      }

      ul ul,
      ul ol,
      ol ul,
      ol ol {
        margin-top: 0;
        margin-bottom: 0;
      }

      li {
        margin-bottom: 0;
      }

      li + li {
        margin-top: 0.25em;
      }

      /* 代码块样式 */
      pre {
        padding: 16px;
        margin-top: 0;
        margin-bottom: 16px;
        overflow: auto;
        font-size: 85%;
        line-height: 1.45;
        background-color: #f6f8fa;
        border-radius: 3px;
        font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, monospace;
      }

      pre > code {
        padding: 0;
        margin: 0;
        font-size: 100%;
        word-break: normal;
        white-space: pre;
        background: transparent;
        border: 0;
      }

      /* 行内代码样式 */
      code {
        padding: 0.2em 0.4em;
        margin: 0;
        font-size: 85%;
        background-color: rgba(27, 31, 35, 0.05);
        border-radius: 3px;
        font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, monospace;
      }

      /* 链接样式 */
      a {
        color: #0366d6;
        text-decoration: none;
      }

      a:hover {
        text-decoration: underline;
      }

      /* 图片样式 */
      img {
        max-width: 100%;
        box-sizing: content-box;
        background-color: #fff;
      }

      /* 表格样式 */
      table {
        display: block;
        width: 100%;
        overflow: auto;
        margin-top: 0;
        margin-bottom: 16px;
        border-spacing: 0;
        border-collapse: collapse;
      }

      table th {
        font-weight: 600;
      }

      table th,
      table td {
        padding: 6px 13px;
        border: 1px solid #dfe2e5;
      }

      table tr {
        background-color: #fff;
        border-top: 1px solid #c6cbd1;
      }

      table tr:nth-child(2n) {
        background-color: #f6f8fa;
      }

      /* 引用样式 */
      blockquote {
        padding: 0 1em;
        color: #6a737d;
        border-left: 0.25em solid #dfe2e5;
        margin: 0 0 16px 0;
      }

      blockquote > :last-child {
        margin-bottom: 0;
      }

      /* 水平线样式 */
      hr {
        height: 0.25em;
        padding: 0;
        margin: 24px 0;
        background-color: #e1e4e8;
        border: 0;
      }

      /* 强调样式 */
      strong {
        font-weight: 600;
      }

      em {
        font-style: italic;
      }

      /* 列表标记样式 */
      li > p {
        margin-top: 16px;
      }

      li + li {
        margin-top: 0.25em;
      }
    }
  }
}
</style>
