<template>
  <div class="follow-flag">
    <el-button
      round
      v-if="followFlag"
      @click="changeFollowFlag($event)"
      class="follow-btn followed"
    >
      已关注
    </el-button>
    <el-button @click="changeFollowFlag($event)" round v-else class="follow-btn"> 关注 </el-button>
  </div>
</template>

<script setup>
import { getFollowFlag, addFollow, delFollow } from "../api/follow";
import { useUserStore } from "../store/user";
import { storeToRefs } from "pinia";
import { ref, onMounted, inject } from "vue";

// pinia
const { userData } = storeToRefs(useUserStore());

// 定义 props
const props = defineProps({
  otherUserId: {
    type: [String, Number],
    required: true,
  },
});
// 从父组件注入方法
if (inject("getFollowList")) {
  const getFollowList = inject("getFollowList");
}
const followFlag = ref(false); // 是否关注

// 关注
const changeFollowFlag = async (event) => {
  event.stopPropagation();
  const params = {
    userId: userData.value.userId,
    followUserId: props.otherUserId,
  };
  if (!followFlag.value) {
    // 添加关注
    await addFollow(params).then((res) => {
      followFlag.value = true;
      if (getFollowFlag) {
        getFollowList(params.userId);
      }
    });
  } else {
    // 取消关注
    let id = props.otherUserId;
    await delFollow({ userId: id }).then((res) => {
      followFlag.value = false;
      if (getFollowFlag) {
        getFollowList(params.userId);
      }
    });
  }
};

// 是否关注
const FollowFlag = async () => {
  let id = props.otherUserId;
  if (id) {
    await getFollowFlag({ id: id }).then((res) => {
      if (res.data.code == "OK") {
        followFlag.value = res.data.data;
      } else {
        followFlag.value = false;
      }
    });
  }
};

onMounted(() => {
  FollowFlag();
});
</script>

<style lang="less" scoped>
.follow-flag {
  .follow-btn {
    background: var(--gradient-btn);
    border: var(--border-tech);
    color: var(--tech-text);
    font-weight: 600;
    transition: var(--transition-normal);
    position: relative;
    overflow: hidden;

    &::before {
      content: '';
      position: absolute;
      top: 0;
      left: -100%;
      width: 100%;
      height: 100%;
      background: linear-gradient(90deg, transparent, rgba(100, 255, 218, 0.3), transparent);
      transition: var(--transition-slow);
    }

    &:hover {
      background: var(--gradient-btn-hover);
      border: var(--border-glow);
      box-shadow: var(--shadow-glow);
      transform: translateY(-2px);

      &::before {
        left: 100%;
      }
    }

    &.followed {
      background: var(--gradient-btn-active);
      color: var(--tech-accent);
      border: 1px solid var(--tech-accent);

      &:hover {
        background: var(--gradient-btn-active-hover);
        box-shadow: 0 0 20px rgba(100, 255, 218, 0.4);
      }
    }
  }
}
</style>
