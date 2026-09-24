<template>
  <div class="exercises-container">

    <!-- 筛选区域 -->
    <div class="filter-section">
      <div class="filter-row">

        
        <div class="filter-group">
            <el-input
            v-model="searchKeyword"
            placeholder="搜索题目..."
            prefix-icon="Search"
            clearable
            @input="handleSearch"
          />
          <span class="filter-label">标签：</span>
          <el-select
            v-model="filter.tags"
            multiple
            placeholder="选择标签"
            @change="handleFilter"
          >
            <el-option
              v-for="tag in availableTags"
              :key="tag"
              :label="tag"
              :value="tag"
            />
          </el-select>
          
        </div>
        
        <div class="filter-group">
          <span class="filter-label">排序：</span>
          <el-select v-model="sortBy" @change="handleSort">
            <el-option label="默认排序" value="default" />
            <el-option label="通过率" value="acceptance" />
            <el-option label="提交数" value="submissions" />
          </el-select>
        </div>
      </div>
    </div>

    <!-- 题目列表 -->
    <div class="problems-list">
      <!-- 加载状态 -->
      <div v-if="loading" class="loading-container">
        <el-skeleton :rows="5" animated />
      </div>
      
      <!-- 题目卡片 -->
      <div v-else-if="filteredProblems.length > 0">
        <div class="problem-card" v-for="problem in filteredProblems" :key="problem.id" @click="goToProblem(problem.id)">
          <div class="problem-header">
            <div class="problem-info">
              <h3 class="problem-title">
                {{ problem.title }}
              </h3>
            </div>
               <!-- 算法类型标签 -->
          <div class="problem-tags">
            <span class="tag" v-for="tag in problem.tags" :key="tag">
              {{ tag }}
            </span>
          </div>
          </div>
        </div>
      </div>
      
      <!-- 空状态 -->
      <div v-else class="empty-container">
        <div class="empty-content">
          <el-empty description="暂无题目数据" />
        </div>
      </div>
    </div>

    <!-- 分页 -->
    <div class="pagination-section">
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="totalProblems"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
      />
    </div>

    <!-- 题目详情弹窗 -->
    <el-dialog
      v-model="showDetailDialog"
      :title="currentProblem ? currentProblem.title : ''"
      width="80%"
    >
      <div v-if="currentProblem" class="problem-detail">
        <div class="detail-section">
          <h4>题目描述</h4>
          <div class="problem-description">
            {{ currentProblem.description }}
          </div>
        </div>
        
        <div class="detail-section">
          <h4>输入格式</h4>
          <pre class="code-block">{{ currentProblem.inputFormat }}</pre>
        </div>
        
        <div class="detail-section">
          <h4>输出格式</h4>
          <pre class="code-block">{{ currentProblem.outputFormat }}</pre>
        </div>
        
        <div class="detail-section">
          <h4>样例</h4>
          <div class="sample-case">
            <p><strong>输入：</strong></p>
            <pre class="code-block">{{ currentProblem.sampleInput }}</pre>
            <p><strong>输出：</strong></p>
            <pre class="code-block">{{ currentProblem.sampleOutput }}</pre>
          </div>
        </div>
        
        <div class="detail-actions">
          <el-button @click="showDetailDialog = false">关闭</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getQuestionListPage } from '../api/Exercises'

const router = useRouter()

// 搜索关键词
const searchKeyword = ref('')

// 筛选条件
const filter = ref({
  tags: []
})

// 排序方式
const sortBy = ref('default')

// 分页
const currentPage = ref(1)
const pageSize = ref(10)
const totalProblems = ref(0) // 后端返回的总题目数

// 弹窗控制
const showDetailDialog = ref(false)
const currentProblem = ref(null)

// 可用标签
const availableTags = [
  '数组', '字符串', '哈希表', '动态规划', '数学', '排序', '贪心',
  '树', '深度优先搜索', '广度优先搜索', '二分查找', '双指针', '栈',
  '队列', '链表', '递归', '回溯', '图', '位运算'
]

// 题目数据
const problems = ref([])

// 加载状态
const loading = ref(false)

// 获取题目列表
const fetchQuestionList = async () => {
  loading.value = true
  try {
    console.log('开始调用API，参数:', { current: currentPage.value, size: pageSize.value })
    
    const response = await getQuestionListPage({
      current: currentPage.value,
      pageSize: pageSize.value
    })
    // console.log('API响应数据:', response) // 调试信息
    // // 检查API响应数据是否有效
    // console.log('完整的API响应:', response)
    // console.log('响应数据:', response.data)
    if (response && response.data && response.data.code === 0 && response.data.data && response.data.data.records && Array.isArray(response.data.data.records)) {
      // 转换API返回的数据格式
      problems.value = response.data.data.records.map(item => ({
        id: item.id,
        title: item.title,
        difficulty: 'medium', // API返回数据中没有difficulty字段，使用默认值
        tags: item.tags || [],
        acceptanceRate: item.acceptedNum > 0 && item.submitNum > 0 ? (item.acceptedNum / item.submitNum) * 100 : 0,
        submissions: item.submitNum || 0,
        accepted: item.acceptedNum || 0,
        description: item.content || '', // API返回的是content字段
        inputFormat: '', // API返回数据中没有inputFormat字段
        outputFormat: '', // API返回数据中没有outputFormat字段
        sampleInput: '', // API返回数据中没有sampleInput字段
        sampleOutput: '' // API返回数据中没有sampleOutput字段
      }))
      
      // 设置后端返回的总题目数
      totalProblems.value = response.data.data.total || 0
      
      console.log('转换后的题目数据:', problems.value) // 调试信息
      console.log('总题目数:', totalProblems.value) // 调试信息
    } else {
      console.error('API响应数据格式错误，实际响应结构:', response)
      problems.value = []
      totalProblems.value = 0
    }
    
  } catch (error) {
    console.error('获取题目列表时发生错误:', error)
    console.error('错误详情:', error.response || error.message)
    
    // 如果是401错误（未授权），提示用户登录
    if (error.response && error.response.status === 401) {
      console.warn('用户未登录或token无效，请先登录')
      // 这里可以添加跳转到登录页面的逻辑
    }
    
    // 如果发生错误，清空题目列表
    problems.value = []
    totalProblems.value = 0
  } finally {
    loading.value = false
  }
}



// 处理搜索
const handleSearch = () => {
  currentPage.value = 1
  fetchQuestionList()
}

// 处理筛选
const handleFilter = () => {
  currentPage.value = 1
  fetchQuestionList()
}

// 处理排序
const handleSort = () => {
  currentPage.value = 1
  fetchQuestionList()
}

// 处理分页
const handlePageChange = (page) => {
  currentPage.value = page
  fetchQuestionList()
}

// 处理页面大小变化
const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1 // 重置到第一页
  fetchQuestionList()
}

// 跳转到题目页面
const goToProblem = (problemId) => {
  router.push(`/problem/${problemId}`)
}

// 显示题目详情
const showProblemDetail = (problem) => {
  currentProblem.value = problem
  showDetailDialog.value = true
}

// 筛选后的题目列表
const filteredProblems = computed(() => {
  let result = problems.value
  
  // 根据搜索关键词筛选
  if (searchKeyword.value) {
    const keyword = searchKeyword.value.toLowerCase()
    result = result.filter(problem => 
      problem.title.toLowerCase().includes(keyword) ||
      problem.tags.some(tag => tag.toLowerCase().includes(keyword))
    )
  }
  

  
  // 根据标签筛选
  if (filter.value.tags.length > 0) {
    result = result.filter(problem => 
      filter.value.tags.every(tag => problem.tags.includes(tag))
    )
  }
  
  // 排序
  switch (sortBy.value) {
    case 'acceptance':
      result.sort((a, b) => b.acceptanceRate - a.acceptanceRate)
      break
    case 'submissions':
      result.sort((a, b) => b.submissions - a.submissions)
      break
    default:
      // 默认按ID排序
      result.sort((a, b) => a.id - b.id)
  }
  
  return result
})

onMounted(() => {
  document.title = '算法练习 - 算法视界'
  fetchQuestionList()
})
</script>

<style lang="less" scoped>
.exercises-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  background: var(--bg-color);
  min-height: 100vh;
}

.exercises-header {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 30px;
  margin-bottom: 20px;
  box-shadow: var(--shadow-card);
  
  .header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    
    .page-title {
      font-size: 2.5rem;
      font-weight: 600;
      color: var(--text-primary);
      background: var(--primary-gradient);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }
    
    .search-bar {
      width: 300px;
    }
  }
}

.filter-section {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: var(--shadow-card);
  
  .filter-row {
    display: flex;
    gap: 30px;
    align-items: center;
    flex-wrap: wrap;
    
    .filter-group {
      display: flex;
      align-items: center;
      gap: 10px;
      
      .filter-label {
        font-weight: 500;
        color: var(--text-secondary);
        white-space: nowrap;
      }
    }
  }
}

.problems-list {
  display: grid;
  gap: 8px;
  margin-bottom: 30px;
}

.loading-container {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 30px;
  margin-bottom: 20px;
  box-shadow: var(--shadow-card);
}

.empty-container {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 60px 30px;
  margin-bottom: 20px;
  box-shadow: var(--shadow-card);
  text-align: center;
  
  .empty-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 20px;
  }
}

.problem-card {
      background: var(--bg-card);
      border: 1px solid var(--border-light);
      border-radius: var(--radius-md);
      padding: 20px;
      transition: all 0.3s ease;
      cursor: pointer;
      display: flex;
      flex-direction: column;
      gap: 12px;
      
      &:hover {
        border-color: var(--primary-color);
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.15);
        transform: translateY(-2px);
      }
      
      .problem-header {
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
        
        .problem-info {
          flex: 1;
          
          .problem-title {
            margin: 0;
            color: var(--text-primary);
            font-size: 1.2rem;
            font-weight: 600;
            line-height: 1.4;
          }
        }
      }
      
      .problem-tags {
        display: flex;
        flex-wrap: wrap;
        gap: 8px;
        margin-top: auto;
        
        .tag {
          background: var(--primary-light);
          color: black;
          padding: 4px 12px;
          border-radius: var(--radius-sm);
          font-size: 0.8rem;
          font-weight: 500;
          border: 1px solid var(--primary-light);
          
          &:hover {
            background: var(--primary-color);
            color: white;
          }
        }
      }
    }

.pagination-section {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

.problem-detail {
  max-height: 60vh;
  overflow-y: auto;
  
  .detail-section {
    margin-bottom: 25px;
    
    h4 {
      color: var(--text-primary);
      margin-bottom: 10px;
      font-weight: 600;
    }
    
    .problem-description {
      line-height: 1.6;
      color: var(--text-secondary);
    }
    
    .code-block {
      background: var(--bg-hover);
      border: 1px solid var(--border-color);
      border-radius: var(--radius-md);
      padding: 15px;
      font-family: 'Courier New', monospace;
      white-space: pre-wrap;
      color: var(--text-primary);
      margin: 10px 0;
    }
    
    .sample-case {
      background: var(--bg-section);
      border-radius: var(--radius-md);
      padding: 15px;
      
      p {
        margin: 0 0 5px 0;
        color: var(--text-primary);
      }
    }
  }
  
  .detail-actions {
    display: flex;
    gap: 10px;
    justify-content: flex-end;
    margin-top: 20px;
    padding-top: 20px;
    border-top: 1px solid var(--border-color);
  }
}

@media (max-width: 768px) {
  .exercises-container {
    padding: 10px;
  }
  
  .exercises-header .header-content {
    flex-direction: column;
    gap: 20px;
    
    .search-bar {
      width: 100%;
    }
  }
  
  .filter-section .filter-row {
    flex-direction: column;
    align-items: stretch;
    gap: 15px;
    
    .filter-group {
      justify-content: space-between;
    }
  }
  
  .problem-card .problem-header {
    flex-direction: column;
    gap: 15px;
  }
}
</style>