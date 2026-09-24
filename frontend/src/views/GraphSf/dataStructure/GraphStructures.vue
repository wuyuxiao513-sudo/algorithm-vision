<template>
	<div class="Graphs">
		<!-- 用于显示图表的容器 -->
		<div ref="chart" class="chart-container"></div>
		<!-- 显示最短距离表格 -->
		<div class="distance-table">
			<div class="distance-table-t">
				<h3>图</h3>
				<el-button class="btn_operation" type="primary" @click="drawer2 = true">
					自定义数据
				</el-button>
				<el-button class="btn_operation" type="danger" @click="del">删除选中</el-button>
				<el-button class="btn_operation" @click="initialize">重置</el-button>
				<div class="distance-table-item">
					<label class="btn_operation" for="search-speed">动画速度</label>
					<input
						id="search-speed"
						class="btn_operation"
						v-model.number="searchSpeed"
						type="range"
						min="1"
						max="10"
					/>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改权值弹窗 -->
	<el-dialog
		v-model="updataLinkFlag"
		title="修改权值"
		width="200"
		align-center
		v-if="updataLinkIndex !== -1"
	>
    <span>
      边：
      {{ graphData.links[updataLinkIndex].source }}->{{
			graphData.links[updataLinkIndex].target
		}}
    </span>
		<el-input-number style="margin-top: 5px" v-model="updataLinkValue" size="small" />
		<template #footer>
			<div>
				<el-button @click="updataLinkFlag = false">取消</el-button>
				<el-button type="primary" @click="updataLink"> 确定</el-button>
			</div>
		</template>
	</el-dialog>

	<div class="contain">
		<!-- 添加数据 -->
		<div class="form-section">
			<el-drawer v-model="drawer2" size="20%" direction="rtl">
				<template #header>
					<h4>自定义数据</h4>
				</template>
				<template #default>
					<!-- 添加节点 -->
					<div class="nodes">
						<div class="title_label">
							<h3>📍 添加节点</h3>
							<el-tooltip
								effect="light"
								:raw-content="true"
								content="可单个输入和多个输入<br>单个输入：A<br>多个输入：A,B,C,D"
								placement="top-start"
							>
								<span style="margin: 1px 0 15px 10px">?</span>
							</el-tooltip>
						</div>
						<el-input
							v-model="newNode"
							placeholder="节点名称A或者A,B,C,D"
							class="input"
						></el-input>
						<div class="btns">
							<el-button type="primary" class="node-btn" @click="addNode">
								➖ 删除节点
							</el-button>
							<el-button type="primary" class="node-btn" @click="addNode">
								➕ 添加节点
							</el-button>
						</div>
					</div>
					<!-- 添加边 -->
					<div class="links">
						<div class="title_label">
							<h3>🔗 添加边</h3>
							<el-tooltip
								effect="light"
								:raw-content="true"
								content="可单个输入和多个输入(用换行分隔)<br>单个输入：A->B=1<br>多个输入：<br>A->B=1<br>A->C=2<br>"
								placement="top-start"
							>
								<span style="margin: 1px 0 15px 10px">?</span>
							</el-tooltip>
						</div>
						<div class="link-inputs">
							<el-input
								v-model="newLink"
								class="input"
								:autosize="{ minRows: 5 }"
								type="textarea"
								placeholder="输入A->B=1
                  或者多边添加
                  A->B=1
                  A->C=2
                "
							/>
						</div>
						<div class="btns">
							<el-button type="primary" class="link-btn" @click="addLink">
								➖ 删除边
							</el-button>
							<el-button type="primary" class="link-btn" @click="addLink">
								➕ 添加边
							</el-button>
						</div>
					</div>
				</template>
			</el-drawer>
		</div>
	</div>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue';
import { ElMessage } from 'element-plus';
import * as echarts from 'echarts';

// 控制抽屉显示与隐藏的状态
const drawer2 = ref(false);
// 起始点
const initial_point = ref('');
// 用于存储新增节点的输入数据
const newNode = ref('');
// 用于存储新增边的输入数据
const newLink = ref('');
// 修改边
// 用于存储修改边弹窗
const updataLinkFlag = ref(false);
// 用于存储修改边下标
const updataLinkIndex = ref(-1);
// 用于存储修改边的值
const updataLinkValue = ref(0);

// 引用图表容器和 ECharts 实例
const chart = ref(null);
let myChart = ref(null);

// 速度控制器
const searchSpeed = ref(5);
// 循环跳出
let Loop_out = true;

// 初始图表数据，包括节点和边的信息
const graphData = ref({
	nodes: [
		{ name: 'A', x: 185, y: 140, symbolSize: 60, itemStyle: { color: 'black' } },
		{ name: 'B', x: 200, y: 160, symbolSize: 60, itemStyle: { color: 'black' } },
		{ name: 'C', x: 225, y: 140, symbolSize: 60, itemStyle: { color: 'black' } },
		{ name: 'D', x: 215, y: 175, symbolSize: 60, itemStyle: { color: 'black' } },
		{ name: 'E', x: 250, y: 180, symbolSize: 60, itemStyle: { color: 'black' } },
		{ name: 'F', x: 210, y: 150, symbolSize: 60, itemStyle: { color: 'black' } },
		{ name: 'G', x: 185, y: 175, symbolSize: 60, itemStyle: { color: 'black' } },
		{ name: 'H', x: 245, y: 145, symbolSize: 60, itemStyle: { color: 'black' } },
		{ name: 'J', x: 235, y: 160, symbolSize: 60, itemStyle: { color: 'black' } }
	],
	links: [
		{
			source: 'A',
			target: 'B',
			value: 3,
			lineStyle: { color: 'black', width: 2, curveness: 0 }
		},
		{
			source: 'B',
			target: 'A',
			value: 3,
			lineStyle: { color: 'black', width: 2, curveness: 0.5 }
		},
		{
			source: 'B',
			target: 'D',
			value: 1,
			lineStyle: { color: 'black', width: 2, curveness: 0 }
		},
		{
			source: 'A',
			target: 'C',
			value: 2,
			lineStyle: { color: 'black', width: 2, curveness: 0 }
		},
		{
			source: 'C',
			target: 'D',
			value: 4,
			lineStyle: { color: 'black', width: 2, curveness: 0 }
		},
		{
			source: 'D',
			target: 'E',
			value: 5,
			lineStyle: { color: 'black', width: 2, curveness: 0 }
		},
		{
			source: 'B',
			target: 'F',
			value: 3,
			lineStyle: { color: 'black', width: 2, curveness: 0 }
		},
		{
			source: 'F',
			target: 'H',
			value: 1,
			lineStyle: { color: 'black', width: 2, curveness: 0 }
		},
		{
			source: 'H',
			target: 'E',
			value: 2,
			lineStyle: { color: 'black', width: 2, curveness: 0 }
		},
		{
			source: 'D',
			target: 'G',
			value: 1,
			lineStyle: { color: 'black', width: 2, curveness: 0 }
		}
	]
});

// 根据边的方向计算曲率，以避免边之间的重合
const calculateCurveness = (source, target) => {
	// 通过检查是否有反向边来计算曲率
	const reverseLinkExists = graphData.value.links.some(
		(link) => link.source === target && link.target === source
	);
	return reverseLinkExists ? 0.5 : 0; // 设置较小的曲率值避免重叠
};

// 初始 ECharts 配置
const option = {
	series: [
		{
			type: 'graph',
			layout: 'none',
			data: graphData.value.nodes,
			links: graphData.value.links,
			label: { show: true, fontSize: 24 },
			edgeSymbol: ['none', 'arrow'],
			edgeLabel: { show: true, formatter: (x) => x.data.value, fontSize: 24 },
			draggable: true
		}
	]
};

// 随机生成坐标函数
const getRandomPosition = (minX = 0, maxX = 800, minY = 0, maxY = 600, minDistance = 30) => {
	let x, y;
	let isValidPosition = false;
	// 直到生成一个合法的坐标位置
	while (!isValidPosition) {
		// 生成一个介于 minX 和 maxX 之间的随机数
		x = Math.floor(Math.random() * (maxX - minX + 1)) + minX;
		// 生成一个介于 minY 和 maxY 之间的随机数
		y = Math.floor(Math.random() * (maxY - minY + 1)) + minY;
		// 检查新生成的坐标是否与现有的节点有足够的距离
		isValidPosition = graphData.value.nodes.every((node) => {
			const distance = Math.sqrt((x - node.x) ** 2 + (y - node.y) ** 2); // 计算两个节点之间的距离
			return distance < minDistance; // 如果距离足够大，返回 true
		});
	}
	return { x, y };
};

// 检查坐标是否重复
const isPositionUnique = (x, y) => {
	return !graphData.value.nodes.some((node) => node.x === x && node.y === y);
};

// 添加节点函数
const addNode = () => {
	if (!Loop_out) {
		ElMessage({
			showClose: true,
			message: 'dijkstra正在运行...',
			type: 'success'
		});
		return;
	}
	// 检查节点名称是否为空
	if (!newNode.value.trim()) {
		ElMessage({
			showClose: true,
			message: '节点名称不能为空',
			type: 'error'
		});
		return;
	}

	let newNodes = newNode.value.split(',');

	newNodes = newNodes.map((item) => {
		return {
			name: item, // 节点名称
			x: 0, // 节点 X 坐标
			y: 0, // 节点 Y 坐标
			symbolSize: 60, // 节点大小
			itemStyle: { color: 'gray' } // 节点的样式
		};
	});

	newNodes.forEach((item) => {
		// 检查节点名称是否已存在
		if (graphData.value.nodes.some((node) => node.name === item.name)) {
			ElMessage({
				showClose: true,
				message: `节点${item.name}名称已存在！`,
				type: 'error'
			});
			return;
		}
		// 在生成随机位置时检查是否重复
		let { x, y } = getRandomPosition(0, 800, 0, 600, 60);
		while (!isPositionUnique(x, y)) {
			// 如果位置重复，继续生成新的随机位置
			({ x, y } = getRandomPosition(0, 800, 0, 600, 60));
		}
		item.x = x;
		item.y = y;
		item.itemStyle.color = 'black';

		// 将新节点添加到图数据中
		graphData.value.nodes.push(JSON.parse(JSON.stringify(item)));
	});

	// 重置节点输入框
	newNode.value = '';
	// 更新图表显示
	updateChart();
};

// 添加边函数
const addLink = () => {
	if (!Loop_out) {
		ElMessage({
			showClose: true,
			message: 'dijkstra正在运行...',
			type: 'success'
		});
		return;
	}
	// 将输入字符串按行分割成数组
	let lines = newLink.value.split('\n');
	// 创建一个空数组来存储结果
	let newLinks = [];
	// 遍历每一行
	lines.forEach((line) => {
		// 使用正则表达式匹配源节点、目标节点和权重
		const match = line.match(/^(\w+)->(\w+)=(\d+)$/);
		if (match) {
			// 解构赋值获取源节点、目标节点和权重
			const [, source, target, value] = match;
			// 将解析后的对象添加到结果数组中
			newLinks.push({
				source: source, // 起始节点
				target: target, // 目标节点
				value: parseInt(value, 10), // 将字符串转换为数字
				lineStyle: {
					color: 'black',
					width: 2,
					curveness: calculateCurveness(source, target)
				}
			});
		}
	});
	if (newLinks.length === 0) {
		ElMessage({
			showClose: true,
			message: '输入格式不正确！',
			type: 'error'
		});
		return;
	}
	newLinks.forEach((item) => {
		// 检查输入的起始节点、目标节点和权重是否有效
		if (!item.source.trim() || !item.target.trim() || item.value <= 0) {
			ElMessage({
				showClose: true,
				message: `${item.source}->${item.target}=${item.value}边的起点、终点和权重都必须有效！`,
				type: 'error'
			});
			return;
		}
		// 检查是否已有相同的边
		const exists = graphData.value.links.some(
			(link) => link.source === item.source && link.target === item.target
		);
		if (exists) {
			ElMessage({
				showClose: true,
				message: `${item.source}=>${item.target}这条边已存在！`,
				type: 'error'
			});
			return;
		}
		// 将新边添加到图数据中
		graphData.value.links.push(item);
	});

	// 重置边的输入框
	newLink.value = '';
	// 更新图表显示
	updateChart();
};

// 修改边值
const updataLink = () => {
	graphData.value.links[updataLinkIndex.value].value = updataLinkValue.value;
	// 清空对话框
	updataLinkFlag.value = false;
	updataLinkIndex.value = -1;
	// 刷新图表
	updateChart();
};

// 删除选中节点和边
const del = () => {
	if (!Loop_out) {
		ElMessage({
			showClose: true,
			message: 'dijkstra正在运行...',
			type: 'success'
		});
		return;
	}
	// 过滤掉颜色为红色的节点
	const redNodeNames = graphData.value.nodes
		.filter((node) => node.itemStyle.color === 'red')
		.map((node) => node.name);

	// 更新 nodes 数组，移除颜色为红色以及相关联的节点
	graphData.value.nodes = graphData.value.nodes.filter(
		(node) => !redNodeNames.includes(node.name)
	);

	// 更新 links 数组，移除颜色为红色以及相关联的链接
	graphData.value.links = graphData.value.links.filter((link) => {
		const isSourceRed = redNodeNames.includes(link.source);
		const isTargetRed = redNodeNames.includes(link.target);
		return !isSourceRed && !isTargetRed;
	});
	// 过滤掉颜色为红色的边
	graphData.value.links = graphData.value.links.filter(
		(link) => link.lineStyle.color !== 'red'
	);
	// 更新echart图表
	updateChart();
};

// 运行Dijkstra
const runDijkstra = async () => {
	Loop_out = false;

	if (!graphData.value.nodes.some((node) => node.name === initial_point.value)) {
		ElMessage({
			showClose: true,
			message: `起始点${initial_point.value}名称不存在！`,
			type: 'error'
		});
		Loop_out = true;
		return;
	}

	if (Loop_out) {
		initialize();
		return;
	}

	Loop_out = true;
};

// 高亮当前节点
const highlightNode = (currentNode) => {
	const updatedNodes = graphData.value.nodes.map((node) => ({
		...node,
		itemStyle: {
			...node.itemStyle,
			// 根据当前节点动态添加类
			color: currentNode === node.name ? 'red' : 'black',
			shadowColor: 'red',
			shadowBlur: currentNode === node.name ? 40 : 0
		}
	}));

	myChart.value.setOption({ series: [{ data: updatedNodes }] });
};

// 高亮路径
const highlightPath = async (source, target) => {
	const updatedLinks = graphData.value.links.map((link) => ({
		...link,
		lineStyle: {
			color: link.source === source && link.target === target ? 'orange' : 'black',
			width: link.source === source && link.target === target ? 6 : 2,
			curveness: link.lineStyle.curveness
		}
	}));
	await sleep(searchSpeed.value); // 动画延迟
	myChart.value.setOption({
		series: [{ links: updatedLinks }]
	});
};

// 睡眠函数
const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, 5000 / ms));

// 更新图表
const updateChart = () => {
	myChart.value.setOption({
		series: [
			{
				...option.series[0],
				data: graphData.value.nodes, // 更新节点数据
				links: graphData.value.links // 更新边数据，避免重复渲染
			}
		]
	});
};

// 初始化
const initialize = () => {
	// 循环跳出
	Loop_out = true;
	// 初始化图表
	updateChart();
	initial_point.value = '';
};

// 初始化图表
onMounted(() => {
	myChart.value = echarts.init(chart.value); // 使用 myChart.value 进行初始化
	myChart.value.setOption(option); // 设置初始图表配置
	// 监听节点拖动事件
	myChart.value.on('mouseup', (params) => {
		if (params.dataType === 'node') {
			const nodeIndex = graphData.value.nodes.findIndex(
				(node) => node.name === params.data.name
			);
			if (nodeIndex !== -1) {
				// 使用像素坐标计算实际坐标
				const newPos = myChart.value.convertFromPixel({ seriesIndex: 0 }, [
					params.event.offsetX,
					params.event.offsetY
				]);

				// 更新节点位置到数据模型
				graphData.value.nodes[nodeIndex].x = newPos[0];
				graphData.value.nodes[nodeIndex].y = newPos[1];
			}
			// console.log(graphData.value);
			updateChart();
		}
	});
	// 监听单击选中
	myChart.value.on('click', (params) => {
		if (!Loop_out) {
			ElMessage({
				showClose: true,
				message: 'dijkstra正在运行...',
				type: 'success'
			});
			return;
		}
		if (params.dataType === 'node') {
			const nodeIndex = graphData.value.nodes.findIndex(
				(node) => node.name === params.data.name
			);
			if (nodeIndex !== -1) {
				let color = graphData.value.nodes[nodeIndex].itemStyle.color;
				graphData.value.nodes[nodeIndex].itemStyle.color =
					color === 'red' ? 'black' : 'red';
			}
		}
		if (params.dataType === 'edge') {
			const linkIndex = graphData.value.links.findIndex(
				(link) => link.source === params.data.source && link.target === params.data.target
			);
			if (linkIndex !== -1) {
				let color = graphData.value.links[linkIndex].lineStyle.color;
				graphData.value.links[linkIndex].lineStyle.color =
					color === 'red' ? 'black' : 'red';
			}
		}
		updateChart();
	});
	// 监听双击修改边
	myChart.value.on('dblclick', (params) => {
		if (!Loop_out) {
			ElMessage({
				showClose: true,
				message: 'dijkstra正在运行...',
				type: 'success'
			});
			return;
		}
		if (params.dataType === 'edge') {
			const linkIndex = graphData.value.links.findIndex(
				(link) => link.source === params.data.source && link.target === params.data.target
			);
			if (linkIndex !== -1) {
				updataLinkFlag.value = true;
				updataLinkIndex.value = linkIndex;
				updataLinkValue.value = graphData.value.links[linkIndex].value;
			}
		}
	});
	// 绑定 resize 事件监听器
	window.addEventListener('resize', resizeHandler);
});

// 根据窗口大小来改变图表大小
const resizeHandler = () => {
	// 确保 myChart 已经初始化
	if (myChart.value) {
		myChart.value.resize(); // 调整图表尺寸
		updateChart(); // 重新更新图表（如果有需要的话）
	}
};

onBeforeUnmount(() => {
	window.removeEventListener('resize', resizeHandler); // 解绑事件监听器
	if (myChart.value) {
		myChart.value.dispose(); // 销毁图表实例
	}
});
</script>

<style lang="less" scoped>
.contain {
	.nodes,
	.links {
		flex: 1;
		padding: 0.625rem;
		margin-bottom: 0.5rem;
		background-color: #ffffff;
		border-radius: 0.5rem;
		border: 1px solid #ddd;
	}

	h3 {
		margin-bottom: 0.9375rem;
		font-size: 1rem;
		font-weight: bold;
		color: #333;
	}

	.input,
	.coordinate-input,
	.weight-input {
		margin-bottom: 0.625rem;
		width: 100%;
	}

	.title_label {
		width: 100%;
		height: 100%;
		display: flex;
		align-items: center;
	}

	.btns {
		width: 100%;
		height: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.node-btn,
	.link-btn {
		margin-top: 0.625rem;
		width: 100%;
		background-color: #409eff;
		border: none;
		border-radius: 0.3125rem;
		padding: 0.625rem;
		font-size: 0.875rem;
		font-weight: bold;
		color: #fff;
		cursor: pointer;
		transition: all 0.3s;
	}

	.node-btn:hover,
	.link-btn:hover {
		background-color: #66b1ff;
	}
}

.Graphs {
	height: 80vh;
	display: flex;

	.distance-table {
		width: 20vw;
		height: 80vh;
		margin-top: 1.25rem;
		padding: 0.625rem;
		display: flex;
		align-items: center; /* 垂直居中 */

		.distance-table-t {
			width: 100%;
			height: 100%;

			.btn {
				width: 100%;
				display: flex;
			}

			.btn_operation {
				width: 12rem;
				margin: 0.3125rem auto;
			}

			.distance-table-item {
				display: flex;
				align-items: center;
				justify-content: center;
				width: 12rem;
				margin: 5px 0 0 5px;
			}

			h3 {
				width: 12rem;
				font-size: 1.2rem;
				text-align: center;
			}
		}
	}

	.chart-container {
		width: 76vw;
		height: 80vh;
		margin-top: 1.25rem;
	}
}
</style>
