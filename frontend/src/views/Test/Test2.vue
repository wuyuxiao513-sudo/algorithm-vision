<template>
	<div class="hh">
		<!--	可视化区	-->
		<div class="Graphs_area">
			<div ref="chart" class="chart-container"></div>
		</div>
		<!--	运行步骤区	-->
		<div class="Steps_area">
			<div>
				<pre class="content"><b>状态</b></pre>
				<div :class="['run_steps','run_steps_bgc2']">
					<pre class="content">{{ status_step }}</pre>
				</div>
				<pre class="content"><b>步骤</b></pre>
				<div
					v-for="(run_steps, index) in paragraph"
					:key="index"
					:class="['run_steps','run_steps_bgc1', { 'selected': selectedIndex === index }]"
				>
					<pre class="content">{{ run_steps }}</pre>
				</div>
			</div>
		</div>
	</div>
	<div class="tt">
		<!--	功能按钮区	-->
		<div class="Function_area">
			<el-popover :width="500" trigger="click" placement="top-end">
				<template #reference>
					<el-button type="primary" :disabled="isRunning">自定义数组</el-button>
				</template>
				<div style="display: flex; align-items: center">
					<el-input v-model="inputArray" placeholder="请输入数组，如: 1,3,4,2,7,5,9"
							  controls-position="right" />
					<el-button type="primary" @click="setArray">自定义</el-button>
					<el-button type="primary" :icon="Delete" @click="clearData">清空</el-button>
				</div>
			</el-popover>
			<el-button type="success" @click="bubbleSort" :disabled="isRunning">运行</el-button>
			<el-button type="warning" @click="pause" :disabled="!isRunning || isPaused">暂停</el-button>
			<el-button type="primary" @click="resume" :disabled="!isPaused">继续</el-button>
			<el-button type="info" @click="prevStep" :disabled="!isPaused || currentStep === 0">上一步</el-button>
			<el-button type="info" @click="nextStep" :disabled="!isPaused || currentStep >= steps.length - 1">下一步
			</el-button>
			<el-button for="search-speed">动画速度</el-button>
			<!-- 速度滑块 -->
			<input
				id="search-speed"
				v-model.number="searchSpeed"
				type="range"
				min="1"
				max="15"
			/>
		</div>
		<!--	预留区	-->
		<div class="Reserved_area">
			预留区
		</div>
	</div>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import { ElMessage } from 'element-plus';
import { Delete } from '@element-plus/icons-vue';

// 引用图表容器和 ECharts 实例
const chart = ref(null);
const myChart = ref(null);

// 数据状态
const inputArray = ref(''); // 用户输入的数组字符串
const data = ref([10, 3, 15, 7, 8, 23, 74, 18, 25, 5]);// 图表数据
const steps = ref([]); // 保存每一步的状态
const indices = ref([]); // 保存每一步的 i 和 j 索引
const currentStep = ref(0); // 当前步骤索引
const isPaused = ref(false); // 是否处于暂停状态
const isRunning = ref(false); // 是否正在运行
const searchSpeed = ref(5);// 速度控制器

// echart配置的数据
const option = {
	xAxis: {
		type: 'category',
		data: Array.from({ length: data.value.length }, (_, i) => `${i}`)
	},
	yAxis: {
		type: 'value',
		axisLine: { show: false }, // 隐藏纵轴线
		axisTick: { show: false }, // 隐藏纵轴刻度
		splitLine: { show: false }, // 隐藏网格线
		axisLabel: { show: false } // 隐藏纵坐标刻度文字
	},
	series: [
		{
			data: data.value.map((value) => ({ value })),
			type: 'bar',
			label: {
				show: true,
				position: 'top',
				color: 'black'
			}
		}
	]
};

// 冒泡排序的循环索引
let n = data.value.length, i = 0, j = 0;

// 设置数组
const setArray = () => {
	try {
		// 以逗号为分隔符，将字符串转换为数组
		let parsedArray = inputArray.value.split(',').map(item => {
			const num = parseFloat(item.trim()); // 转换每个元素为数字，并去除两侧空格
			if (isNaN(num)) {
				throw new Error('包含无效数字');
			}
			return num;
		});

		// 确保转换后的数组是有效的数字数组
		if (Array.isArray(parsedArray) && parsedArray.every(item => typeof item === 'number')) {
			data.value = parsedArray; // 更新数据
			updateChart(); // 更新图表
			resetSorting(); // 重置排序状态
			inputArray.value = '';// 重置图表数据
		} else {
			ElMessage.error('请输入一个有效的数字数组！');
		}
	} catch (error) {
		ElMessage.error('输入格式有误，请确保输入类似于 1,2,3,4,2,4 的格式！');
	}
};

// 清理数组数据
const clearData = () => {
	data.value = [];// 重置图表数据
	inputArray.value = '';// 重置图表数据
	resetSorting(); // 重置排序状态
	updateChart(); // 更新图表
};

// 重置排序状态
const resetSorting = () => {
	steps.value = [];
	indices.value = [];
	currentStep.value = 0;
	i = 0;
	j = 0;
	n = data.value.length;
};

// 更新图表
const updateChart = (
	highlightIndex1 = null,
	highlightIndex2 = null,
	sortedCount = 0,
	color = 'black'
) => {
	// 映射数据，标记高亮元素并标记已排序的元素
	const chartData = data.value.map((value, index) => ({
		value,
		itemStyle:
			index === highlightIndex1 || index === highlightIndex2
				? { color: color } // 当前比较的元素用指定颜色标记
				: index >= data.value.length - sortedCount
					? { color: 'yellow' } // 已排序好的元素用黄色标记
					: {}
	}));
	// 检查数据是否发生变化，只有在数据有变动时才更新
	if (JSON.stringify(chartData) !== JSON.stringify(myChart.value.getOption().series[0].data)) {
		myChart.value.setOption({
			xAxis: {
				type: 'category',
				data: Array.from({ length: data.value.length }, (_, i) => `${i}`) // 动态调整X轴
			},
			series: [
				{
					data: chartData,
					type: 'bar'
				}
			]
		});
	}
};

// 延时函数，用于暂停等待
const sleep = (ms = 5000) =>
	new Promise((resolve) => setTimeout(resolve, ms / searchSpeed.value));

// 暂停功能
const pause = () => {
	isPaused.value = true;
};

// 继续功能
const resume = () => {
	isPaused.value = false;
};

// 上一步功能
const prevStep = async () => {
	if (currentStep.value > 0) {
		currentStep.value--; // 步骤回退
		data.value = [...steps.value[currentStep.value]]; // 恢复上一步的数组状态
		i = indices.value[currentStep.value].i; // 恢复上一步的 i 索引
		j = indices.value[currentStep.value].j; // 恢复上一步的 j 索引

		// 更新图表
		updateChart(j, j + 1, i, 'red');
	}
};

// 下一步功能
const nextStep = async () => {
	if (currentStep.value < steps.value.length - 1) {
		currentStep.value++; // 步骤前进
		data.value = [...steps.value[currentStep.value]]; // 恢复下一步的数组状态
		i = indices.value[currentStep.value].i; // 恢复下一步的 i 索引
		j = indices.value[currentStep.value].j; // 恢复下一步的 j 索引

		// 更新图表，标红当前比较的两个柱子
		updateChart(j, j + 1, i, 'red');
	}
};

// 冒泡排序算法
const bubbleSort = async () => {
	isRunning.value = true; // 标记排序正在进行
	isPaused.value = false; // 取消暂停状态

	for (; i < n - 1; i++) {
		// 步骤0
		StepsIndex(0, 0);
		editStatusStep('i = ' + i + ';'); // 状态i
		await sleep();

		for (; j < n - 1 - i; j++) {
			//步骤1
			StepsIndex(1, 0);
			await sleep();
			editStatusStep('j = ' + j + ';'); // 状态j

			// 每次循环前检查停止标志
			if (!isRunning.value) break; // 退出循环的条件

			// 暂停时循环等待
			while (isPaused.value) await sleep();

			// 保存当前步骤数据和索引
			steps.value.push([...data.value]);
			indices.value.push({ i, j });

			// 更新当前步骤索引
			currentStep.value = steps.value.length - 1;

			// 更新图表，标红当前比较的两个柱子
			updateChart(j, j + 1, i, 'red');
			await sleep();

			// 步骤2
			StepsIndex(2, 0);
			let Symbol = data.value[j] > data.value[j + 1] ? ' > ' : data.value[j] < data.value[j + 1] ? ' < ' : ' = ';
			editStatusStep(data.value[j] + Symbol + data.value[j + 1]); // 状态比较大小
			await sleep();

			if (data.value[j] > data.value[j + 1]) {

				// 步骤3
				StepsIndex(3, 0);
				await sleep();

				// 交换两个元素
				const temp = data.value[j];
				data.value[j] = data.value[j + 1];
				data.value[j + 1] = temp;

				// 更新图表，显示交换后的状态
				updateChart(j, j + 1, i, 'red');
				await sleep();
			}
		}
		// 标记已经排序好的元素
		updateChart(null, null, i + 1, 'red');
		j = 0;
	}
	// 排序完成后，更新图表并标记为非运行状态
	editStatusStep('排序成功，运行结束！');
	updateChart();
	isRunning.value = false;
};

// 当前状态步骤
const status_step = ref('状态');

// 更新状态步骤
const editStatusStep = (status) => {
	status_step.value = status;
};

// 算法运行步骤解析
const selectedIndex = ref(0);

// 固定步骤库
const paragraphs = [
	[
		'while 从i = 0 到 n - 1',
		'  while 从j = 0 到 当前未排过序元素前(n - 1 - i)',
		'    如果 左边元素 > 右边元素',
		'      交换（左边元素，右边元素）'
	],
	[
		'错误'
	]
];

// 展示步骤数据
const paragraph = ref([]);
paragraph.value = paragraphs[0];

// 步骤区域下标
const StepsIndex = (index, paragraphIndex) => {// 参数1,步骤下标.参数2,步骤库下标
	if (index < 0 || index > paragraphs[paragraphIndex].length) {
		index = 0;
	}
	selectedIndex.value = index;
};

// 在组件挂载时初始化图表
onMounted(() => {
	myChart.value = echarts.init(chart.value); // 使用 myChart.value 进行初始化
	myChart.value.setOption(option); // 设置图表选项
	window.addEventListener('resize', resizeHandler);// 绑定 resize 事件监听器
});

// 根据窗口大小来改变图表大小
const resizeHandler = () => {
	// 确保 myChart 已经初始化
	if (myChart.value) {
		updateChart(); // 更新图表,以确保echart数据初始完整
		myChart.value.resize(); // 调整图表尺寸
	}
};

// 在组件销毁时
onBeforeUnmount(() => {
	// 解绑事件监听器
	window.removeEventListener('resize', resizeHandler);
	if (myChart.value) {
		myChart.value.dispose(); // 销毁图表实例
	}
});

</script>

<style lang="less" scoped>
.hh {
	height: 70vh;
	width: 100vw;
	display: flex;
	align-items: center;

	.Graphs_area {
		flex: 65%;
		display: flex;
		align-items: center;
		justify-content: center;

		.chart-container {
			width: 65vw;
			height: 50vh;
			margin-top: 1.25rem;
		}
	}

	.Steps_area {
		flex: 35%;
		display: flex;
		align-items: center;
		justify-content: center;

		.run_steps {
			width: 500px;
			display: flex;
			align-items: center;
			margin: 0 auto;
			padding: 8px 10px;
			color: white;
		}

		.run_steps_bgc1 {
			background-color: #5ca0df;
		}

		.run_steps_bgc2 {
			margin-bottom: 10px;
			background-color: #42c66a;
		}

		.run_steps.selected {
			background-color: #f37171;
		}

		.content {
			font-size: 18px;
			white-space: pre-wrap; /* 保持空格并允许自动换行 */
			word-wrap: break-word; /* 确保长单词也能换行 */
		}
	}
}

.tt {
	height: 15vh;
	width: 100vw;
	display: flex;
	align-items: center;

	.Function_area {
		flex: 65%;
		display: flex;
		align-items: center;
		justify-content: center;

		.input_arrays {
			display: flex;
		}
	}

	.Reserved_area {
		flex: 35%;
		display: flex;
		align-items: center;
		//justify-content: center;
	}

}

</style>
