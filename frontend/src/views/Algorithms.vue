<template>
  <div v-for="(item, index) in algorithms_lists" :key="index">
    <h3>{{ item.type_name }}</h3>
    <el-row :gutter="20">
      <el-col
        :span="6"
        v-for="(item_chil, index_chil) in item.chart_list"
        :key="index_chil"
      >
        <div class="grid-content ep-bg-purple">{{ item_chil.algorithm_name }}</div>
        <div @click="Goto(item_chil.path, item_chil.algorithm_name)">
          <el-card class="image-card">
            <img :src="item_chil.image_src" class="card-image" />
            <div class="card-overlay">
              <div class="algorithm-name">{{ item_chil.algorithm_name }}</div>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
    <el-divider />
  </div>
</template>

<script setup>
import { ElCard } from "element-plus";

const algorithms_lists = [
  {
    type_name: "排序",
    chart_list: [
      {
        algorithm_name: "冒泡排序",
        image_src: new URL("../assets/images/algorithms/bubbling.gif", import.meta.url)
          .href,
        path: "bubbling_sort",
      },
      {
        algorithm_name: "选择排序",
        image_src: new URL("../assets/images/algorithms/list_px.gif", import.meta.url)
          .href,
        path: "selection_sort",
      },
      {
        algorithm_name: "插入排序",
        image_src: new URL(
          "../assets/images/algorithms/insertion_sort.gif",
          import.meta.url
        ).href,
        path: "insertion_sort",
      },
      {
        algorithm_name: "快速排序",
        image_src: new URL("../assets/images/algorithms/quick_sort.gif", import.meta.url)
          .href,
        path: "quick_sort",
      },
    ],
  },
  {
    type_name: "图论",
    chart_list: [
      {
        algorithm_name: "广度优先搜索(图的遍历)",
        image_src: new URL("../assets/images/algorithms/bfs-grid.gif", import.meta.url)
          .href,
        path: "bfsGrid",
      },
      {
        algorithm_name: "深度优先搜索(图的遍历)",
        image_src: new URL("../assets/images/algorithms/dfsGrid.gif", import.meta.url)
          .href,
        path: "dfsGrid",
      },
      {
        algorithm_name: "Dijkstra最短路",
        image_src: new URL("../assets/images/algorithms/dijkstra.gif", import.meta.url)
          .href,
        path: "dijkstra",
      },
      // {
      //   algorithm_name: "Floyd最短路",
      //   image_src: new URL("../assets/images/graph.png", import.meta.url).href,
      //   path: "bubbling_sort",
      // },
    ],
  },
  // {
  //   type_name: "最小生成树",
  //   chart_list: [
  //     {
  //       algorithm_name: "Prim算法",
  //       image_src: new URL("../assets/images/tree.png", import.meta.url).href,
  //       path: "bubbling_sort",
  //     },
  //     {
  //       algorithm_name: "Kruskal算法",
  //       image_src: new URL("../assets/images/tree.png", import.meta.url).href,
  //       path: "bubbling_sort",
  //     },
  //   ],
  // },
  {
    type_name: "数据结构",
    chart_list: [
      {
        algorithm_name: "链表",
        image_src: new URL("../assets/images/algorithms/linkList.gif", import.meta.url)
          .href,
        path: "linkList",
      },
      {
        algorithm_name: "栈",
        image_src: new URL("../assets/images/algorithms/stack.gif", import.meta.url).href,
        path: "stack",
      },
      {
        algorithm_name: "队列",
        image_src: new URL("../assets/images/algorithms/queue.gif", import.meta.url).href,
        path: "queue",
      },
      // {
      //   algorithm_name: "树",
      //   image_src: new URL("../assets/images/list_px.png", import.meta.url).href,
      //   path: "bubbling_sort",
      // },
      {
        algorithm_name: "图结构",
        image_src: new URL(
          "../assets/images/algorithms/GraphStructures.gif",
          import.meta.url
        ).href,
        path: "GraphStructures",
      },
      // {
      //   algorithm_name: "并查集",
      //   image_src: new URL("../assets/images/list_px.png", import.meta.url).href,
      //   path: "bubbling_sort",
      // },
      // {
      //   algorithm_name: "Hash表",
      //   image_src: new URL("../assets/images/list_px.png", import.meta.url).href,
      //   path: "bubbling_sort",
      // },
    ],
  },
];

import { useRouter } from "vue-router";
const router = useRouter(); // // 使用 useRouter 函数获取路由

const Goto = (path, algorithm_name) => {
  // 获取需要跳转的链接
  const routeData = router.resolve({
    path: path,
    query: { algorithm_name: algorithm_name },
  });
  // 打开新标签页,跳转到内容页
  window.open(routeData.href, "_blank");
};
</script>

<style lang="less" scoped>
div {
  animation: fadeIn 0.8s ease-out;
}

h3 {
  color: var(--text-primary);
  font-size: 24px;
  font-weight: 600;
  margin: 30px 0 20px 0;
  padding-left: 20px;
  position: relative;

  &::before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 4px;
    height: 20px;
    background: var(--primary-gradient);
    border-radius: 2px;
  }
}

.el-row {
  margin-bottom: 30px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: var(--radius-md);
  margin-bottom: 20px;

  .grid-content {
    border-radius: var(--radius-md);
    min-height: 36px;
    color: var(--text-secondary);
    font-size: 14px;
    text-align: center;
    margin-bottom: 10px;
    font-weight: 500;
  }
}

.image-card {
  width: 100%;
  height: 200px;
  overflow: hidden;
  position: relative;
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-card);
  transition: all var(--transition-normal);
  cursor: pointer;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(102, 126, 234, 0.2), transparent);
    transition: var(--transition-slow);
    z-index: 1;
  }

  &:hover {
    transform: translateY(-8px);
    box-shadow: var(--shadow-lg);
    border-color: var(--primary-color);

    &::before {
      left: 100%;
    }

    .card-image {
      transform: scale(1.1);
      opacity: 0.9;
    }

    .card-overlay {
      opacity: 1;
      transform: translateY(0);
    }
  }

  .card-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: var(--transition-normal);
    filter: brightness(0.8);
  }

  .card-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: linear-gradient(to bottom, transparent, rgba(10, 25, 47, 0.9));
    display: flex;
    align-items: flex-end;
    padding: 20px;
    opacity: 0;
    transform: translateY(20px);
    transition: var(--transition-normal);
    z-index: 2;

    .algorithm-name {
      color: white;
      font-size: 16px;
      font-weight: 600;
      text-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
    }
  }
}

.el-divider {
  border-color: var(--border-color);
  margin: 40px 0;

  &::before, &::after {
    background-color: var(--primary-color);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  h3 {
    font-size: 20px;
    margin: 20px 0 15px 0;
    padding-left: 15px;
  }

  .el-col {
    margin-bottom: 15px;

    .grid-content {
      font-size: 13px;
    }
  }

  .image-card {
    height: 160px;

    .card-overlay {
      padding: 15px;

      .algorithm-name {
        font-size: 14px;
      }
    }
  }

  .el-divider {
    margin: 30px 0;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}


</style>
