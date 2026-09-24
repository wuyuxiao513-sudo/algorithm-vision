<template>
  <span class="time-ago">{{ formattedTime }}</span>
</template>

<script>
import { computed } from "vue";
import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import timezone from "dayjs/plugin/timezone";

// 使用 dayjs 插件
dayjs.extend(utc);
dayjs.extend(timezone);

export default {
  name: "TimeAgo",
  props: {
    timestamp: {
      type: [String, Number],
      required: true,
    },
  },
  setup(props) {
    const formattedTime = computed(() => {
      return formatTime(props.timestamp);
    });

    const formatTime = (timestamp) => {
      const now = dayjs(); // 获取当前时间
      const date = dayjs(timestamp).tz("Asia/Shanghai"); // 将时间转换为指定时区（例如：北京时间）
      const diffInSeconds = now.diff(date, "second");
      const diffInMinutes = now.diff(date, "minute");
      const diffInHours = now.diff(date, "hour");
      const diffInDays = now.diff(date, "day");

      if (diffInSeconds < 60) {
        return "刚刚"; // 小于 60 秒
      } else if (diffInMinutes < 60) {
        return `${diffInMinutes} 分钟前`;
      } else if (diffInHours < 24) {
        return `${diffInHours} 小时前`;
      } else if (diffInDays === 1) {
        return "昨天";
      } else if (diffInDays === 2) {
        return "前天";
      } else if (diffInDays < 3) {
        return `${diffInDays} 天前`;
      } else {
        return date.format("YYYY-MM-DD"); // 超过 3 天，显示具体日期
      }
    };

    return {
      formattedTime,
    };
  },
};
</script>

<style scoped>
.time-ago {
  color: var(--tech-text-secondary);
  font-size: 12px;
  font-weight: 500;
  opacity: 0.8;
  transition: var(--transition-fast);

  &:hover {
    color: var(--tech-accent);
    opacity: 1;
    text-shadow: 0 0 8px rgba(100, 255, 218, 0.5);
  }
}
</style>
