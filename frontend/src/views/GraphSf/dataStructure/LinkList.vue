<template>
	<div class="Graphs">
		<!-- 用于显示图表的容器 -->
		<div ref="chart" class="chart-container"></div>
		<!-- 操作区 -->
		<div class="operate-btn">
			<div class="operate-btn-t">
				<h3>链表</h3>
				<!-- 新建 -->
				<el-popover placement="left" :width="300" trigger="click">
					<template #reference>
						<el-button class="btn_operation" type="primary"> 新建</el-button>
					</template>
					<!-- 输入查找数据 -->
					<div style="display: flex">
						<el-input v-model="addData" size="small" />
						<el-button type="primary" @click="addNode"> 自定义</el-button>
						<el-button type="primary" :icon="Delete" @click="clearData">清空</el-button>
					</div>
				</el-popover>
				<!-- 查询 -->
				<el-popover placement="left" :width="200" trigger="click">
					<template #reference>
						<el-button class="btn_operation" type="success"> 查找</el-button>
					</template>
					<!-- 输入查找数据 -->
					<div style="display: flex; align-items: center">
						<span>v=</span>
						<el-input-number v-model="findData" controls-position="right" />
						<el-button type="success" :icon="Search" @click="find"> 查找</el-button>
					</div>
				</el-popover>
				<!-- 插入 -->
				<el-popover placement="left" :width="300" trigger="click">
					<!-- 插入按钮 -->
					<template #reference>
						<el-button class="btn_operation" type="warning"> 插入</el-button>
					</template>
					<!-- 头插 -->
					<el-popover :width="200" trigger="click" placement="top-start">
						<template #reference>
							<el-button type="warning"> 头插入</el-button>
						</template>
						<div style="display: flex; align-items: center">
							<span>v=</span>
							<el-input-number v-model="insertData.value" controls-position="right" />
							<el-button type="warning" @click="headInsert"> 头插入</el-button>
						</div>
					</el-popover>
					<!-- 尾插 -->
					<el-popover :width="200" trigger="click" placement="top">
						<template #reference>
							<el-button type="warning"> 尾插入</el-button>
						</template>
						<div style="display: flex; align-items: center">
							<span>v=</span>
							<el-input-number v-model="insertData.value" controls-position="right" />
							<el-button type="warning" @click="tailInsert"> 尾插入</el-button>
						</div>
					</el-popover>
					<!-- 中间插 -->
					<el-popover :width="300" trigger="click" placement="top-end">
						<template #reference>
							<el-button type="warning"> 中间插入</el-button>
						</template>
						<div style="display: flex; align-items: center">
							<span>v=</span>
							<el-input-number v-model="insertData.value" controls-position="right" />
							<span>i=</span>
							<el-input-number v-model="insertData.index" controls-position="right" />
							<el-button type="warning" @click="Insert"> 插入</el-button>
						</div>
					</el-popover>
				</el-popover>
				<!-- 移除 -->
				<el-popover placement="left" :width="300" trigger="click">
					<!-- 移除按钮 -->
					<template #reference>
						<el-button class="btn_operation" type="danger"> 移除</el-button>
					</template>
					<!-- 移除头 -->
					<el-button type="danger" @click="headDel"> 移除头</el-button>
					<!-- 移除尾 -->
					<el-button type="danger" @click="tailDel"> 移除尾</el-button>
					<!-- 移除中间 -->
					<el-popover :width="200" trigger="click" placement="top-end">
						<template #reference>
							<el-button type="danger"> 移除中间</el-button>
						</template>
						<div style="display: flex; align-items: center">
							<span>i=</span>
							<el-input-number v-model="removeData.index" controls-position="right" />
							<el-button type="danger" @click="Del"> 移除指定i</el-button>
						</div>
					</el-popover>
				</el-popover>
				<!-- 动画速度 -->
				<div style="display: flex">
					<el-button>动画速度:</el-button>
					<input v-model.number="searchSpeed" type="range" min="1" max="10" />
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { Delete, Search } from '@element-plus/icons-vue';
import * as echarts from 'echarts';

// 引用图表容器和 ECharts 实例
const chart = ref(null);
let myChart = ref(null);

// 速度控制器
const searchSpeed = ref(5);
// 新建
const addData = ref();
// 查找
const findData = ref(0); //查找的数据
let find_run = false; // 查找是否在运行
// 插入
const insertData = ref({ index: null, value: null }); // 插入的数据
// 移除
const removeData = ref({ index: null }); // 移除的位置

// 初始图表数据，包括节点和边的信息
const graphData = ref({
	nodes: [
		{
			id: 0,
			name: '10',
			x: 0,
			y: 100,
			index: 'head/0',
			symbolSize: 40,
			itemStyle: { color: 'black' }
		},
		{
			id: 1,
			name: '-23',
			x: 10,
			y: 100,
			index: '',
			symbolSize: 40,
			itemStyle: { color: 'black' }
		},
		{
			id: 2,
			name: '45',
			x: 20,
			y: 100,
			index: '',
			symbolSize: 40,
			itemStyle: { color: 'black' }
		},
		{
			id: 3,
			name: '78',
			x: 30,
			y: 100,
			index: '',
			symbolSize: 40,
			itemStyle: { color: 'black' }
		},
		{
			id: 4,
			name: '9',
			x: 40,
			y: 100,
			index: '',
			symbolSize: 40,
			itemStyle: { color: 'black' }
		},
		{
			id: 5,
			name: '4',
			x: 50,
			y: 100,
			index: '',
			symbolSize: 40,
			itemStyle: { color: 'black' }
		},
		{
			id: 6,
			name: '6',
			x: 60,
			y: 100,
			index: '',
			symbolSize: 40,
			itemStyle: { color: 'black' }
		},
		{
			id: 7,
			name: '6',
			x: 70,
			y: 100,
			index: 'tail/7',
			symbolSize: 40,
			itemStyle: { color: 'black' }
		}
	],
	links: [
		{ source: '0', target: '1', lineStyle: { color: 'black', width: 2 } },
		{ source: '1', target: '2', lineStyle: { color: 'black', width: 2 } },
		{ source: '2', target: '3', lineStyle: { color: 'black', width: 2 } },
		{ source: '3', target: '4', lineStyle: { color: 'black', width: 2 } },
		{ source: '4', target: '5', lineStyle: { color: 'black', width: 2 } },
		{ source: '5', target: '6', lineStyle: { color: 'black', width: 2 } },
		{ source: '6', target: '7', lineStyle: { color: 'black', width: 2 } }
	]
});

// 初始 ECharts 配置
const option = {
	series: [
		{
			type: 'graph',
			layout: 'none',
			data: graphData.value.nodes,
			links: graphData.value.links,
			label: {
				show: true,
				fontSize: 20,
				formatter: function(params) {
					return '\n\n' + params.data.name + '\n\n' + params.data.index; // 拼接节点名称和自定义数据显示
				}
			},
			edgeSymbol: ['none', 'arrow'],
			edgeSymbolSize: 7,
			// edgeLabel: { show: true, formatter: (x) => x.data.value, fontSize: 24 },
			draggable: true
		}
	]
};

// 清空数据
const clearData = () => {
	graphData.value.nodes = [];
	graphData.value.links = [];
	updateChart();
};

// 新建函数
const addNode = () => {
	// 检查节点名称是否为空
	if (!addData.value.trim()) {
		ElMessage({
			showClose: true,
			message: '不能为空',
			type: 'error'
		});
		return;
	}
	// 清除之前的数据
	clearData();

	// 新建数据
	// 将输入的节点数据按照逗号分割
	graphData.value.nodes = addData.value.split(',').map((item, index) => {
		// 如果是第一个节点，标记为 head
		const isHead = index === 0;
		const isTail = index === addData.value.split(',').length - 1; // 如果是最后一个节点，标记为 tail
		return {
			id: graphData.value.nodes.length + index, // 新节点ID自动递增
			name: item.trim(), // 节点名称
			x: index * 10, // 每个节点在X轴上的位置，假设节点在X轴上分布
			y: 100, // 节点Y轴位置固定为100
			index: isHead
				? `head/0`
				: isTail
					? `tail/${graphData.value.nodes.length + index}`
					: '', // 头节点和尾节点的 index
			symbolSize: 40, // 默认节点大小
			itemStyle: { color: 'black' } // 节点颜色
		};
	});
	// 更新链接：为每个新节点与前一个节点添加连接
	graphData.value.links = graphData.value.nodes.map((link) => {
		return {
			source: `${link.id}`,
			target: `${link.id + 1}`,
			lineStyle: { color: 'black', width: 2 }
		};
	});
	// 抛出最后一个错误数据
	graphData.value.links.pop();
	// console.log(graphData.value.nodes);
	// console.log(graphData.value.links);

	// 重置新建输入框
	addData.value = '';
	// 更新图表显示
	updateChart();
};

// 查找
const find = async () => {
	if (find_run) {
		ElMessage.error('正在查找');
		return;
	}
	find_run = true;
	updateChart(); // 重置

	// 获取链表中的节点名称
	const targetValue = findData.value;
	let currentNode = graphData.value.nodes[0]; // 从头节点开始
	let index = 0; // 循环下标
	let findFlag = false; // 是否找到

	// 遍历链表，查找目标节点
	while (currentNode) {
		// 高亮当前节点
		await sleep(searchSpeed.value); // 动画延迟
		highlightNode(currentNode);
		highligIndex(index);
		await sleep(searchSpeed.value); // 动画延迟
		// 如果当前节点是目标节点，则停止查找
		if (currentNode.name === targetValue.toString()) {
			ElMessage({
				showClose: true,
				message: `已找到节点 ${targetValue}`,
				type: 'success'
			});
			findFlag = true;
			break;
		}
		// 高亮当前路径
		if (currentNode.id < graphData.value.nodes.length - 1) {
			highlightPath(currentNode.id, graphData.value.nodes[index + 1].id);
			await sleep(searchSpeed.value); // 动画延迟
		}
		// 移动到下一个节点
		currentNode = graphData.value.nodes[index + 1];
		index += 1;
	}

	if (!findFlag) {
		ElMessage.error(`没找到节点 ${targetValue}`);
	}

	find_run = false;
	findData.value = 0;
};

// 插入
// 头插入
const headInsert = async () => {
	// 如果没有输入插入的节点值，则提示错误
	if (insertData.value.value === null) {
		ElMessage.error('请输入要插入的节点值！');
		return;
	}

	// 1. 创建新的节点高亮头插入的节点
	const newNode = {
		id: graphData.value.nodes.length, // 新节点ID为当前节点数量
		name: insertData.value.value.toString(), // 节点值
		x: graphData.value.nodes[0].x - 10, // 位置可以根据需要调整
		y: 100,
		index: '', // 设置头节点标识
		symbolSize: 40,
		itemStyle: { color: '#E6A23C' }
	};

	// 2. 更新节点数据，将新节点生成到头部
	graphData.value.nodes.unshift(newNode);
	myChart.value.setOption({ series: [{ data: graphData.value.nodes }] });
	await sleep(searchSpeed.value); // 延迟动画

	// 3. 更新链接数据，高亮路径，头插入时需要调整现有的 `links`，将新节点与原头节点连接
	const newLink = {
		source: newNode.id.toString(),
		target: graphData.value.nodes[1].id.toString(),
		lineStyle: { color: 'orange', width: 6 }
	};
	graphData.value.links.unshift(newLink); // 插入新边
	myChart.value.setOption({ series: [{ links: graphData.value.links }] });
	await sleep(searchSpeed.value); // 延迟动画

	// console.log(graphData.value.nodes);
	// console.log(graphData.value.links);

	// 4. 更新头节点和尾节点的下标
	// 获取节点总数
	const nodeCount = graphData.value.nodes.length;
	// 更新头节点的 index (假设头节点是第一个节点)
	graphData.value.nodes[0].index = 'head/0';
	graphData.value.nodes[1].index = '';
	// 更新尾节点的 index (假设尾节点是最后一个节点)
	graphData.value.nodes[nodeCount - 1].index = `tail/${nodeCount - 1}`;
	// 5. 更新图表
	updateChart();
	// 重置插入数据
	insertData.value = { index: null, value: null };
};

// 尾插入
const tailInsert = async () => {
	// 如果没有输入插入的节点值，则提示错误
	if (insertData.value.value === null) {
		ElMessage.error('请输入要插入的节点值！');
		return;
	}
	// 获取节点总数
	const nodeCount = graphData.value.nodes.length;
	// 1.创建新的节点 高亮尾插入的节点
	const newNode = {
		id: graphData.value.nodes.length, // 新节点ID为当前节点数量
		name: insertData.value.value, // 节点值
		x: graphData.value.nodes[nodeCount - 1].x + 10, // 每个节点在X轴上的位置，假设节点在X轴上分布
		y: 100,
		index: '',
		symbolSize: 40,
		itemStyle: { color: '#E6A23C' }
	};

	// 2. 更新节点数据，将新节点添加到尾部
	graphData.value.nodes.push(newNode);
	myChart.value.setOption({ series: [{ data: graphData.value.nodes }] });
	await sleep(searchSpeed.value); // 延迟动画

	// 3. 更新链接数据，尾插入时需要调整现有的 `links`，将新节点与原尾节点连接
	const lastNode = graphData.value.nodes[graphData.value.nodes.length - 2];
	const newLink = {
		source: lastNode.id.toString(),
		target: newNode.id.toString(),
		lineStyle: { color: 'orange', width: 6 }
	};
	graphData.value.links.push(newLink); // 插入新边
	myChart.value.setOption({ series: [{ links: graphData.value.links }] });
	await sleep(searchSpeed.value); // 延迟动画
	// 4. 更新尾节点的 index (假设尾节点是最后一个节点)
	graphData.value.nodes[nodeCount - 1].index = '';
	graphData.value.nodes[nodeCount].index = `tail/${nodeCount}`;
	// 5. 更新图表
	updateChart();
	// 重置插入数据
	insertData.value = { index: null, value: null };
};

// 中间插入
const Insert = async () => {
	// 检查输入的插入位置和节点值是否合法
	if (insertData.value.index === null || insertData.value.value === null) {
		ElMessage.error('请输入插入位置和节点值！');
		return;
	}
	const insertIndex = insertData.value.index;
	// 检查插入位置是否在有效范围内
	if (insertIndex < 0 || insertIndex >= graphData.value.nodes.length) {
		ElMessage.error('插入位置不合法！');
		return;
	}

	// 遍历链表，查找目标节点
	for (let index = 0; index < graphData.value.nodes.length; index++) {
		// 高亮当前节点
		await sleep(searchSpeed.value); // 动画延迟
		highlightNode(graphData.value.nodes[index]);
		highligIndex(index);
		await sleep(searchSpeed.value); // 动画延迟
		// 如果当前节点是目标节点，则停止查找
		if (index == insertIndex - 1) {
			break;
		}
		// 高亮当前路径
		highlightPath(graphData.value.nodes[index].id, graphData.value.nodes[index + 1].id);
		await sleep(searchSpeed.value); // 动画延迟
	}

	// 1. 创建新的节点 高亮中间插入的节点
	const newNode = {
		id: graphData.value.nodes.length, // 新节点ID为当前节点数量
		name: insertData.value.value.toString(), // 节点值
		x: graphData.value.nodes[insertIndex].x, // 根据插入位置调整X坐标
		y: 90,
		index: '',
		symbolSize: 40,
		itemStyle: { color: '#E6A23C' }
	};

	// 2. 更新节点数据，将新节点插入到指定位置
	graphData.value.nodes.splice(insertIndex, 0, newNode);
	myChart.value.setOption({ series: [{ data: graphData.value.nodes }] });
	await sleep(searchSpeed.value); // 延迟动画

	// 3. 更新链接数据，在插入位置处创建新连接
	const prevNode = graphData.value.nodes[insertIndex - 1];
	const nextNode = graphData.value.nodes[insertIndex + 1];

	const newLinkPrev = {
		source: prevNode.id.toString(),
		target: newNode.id.toString(),
		lineStyle: { color: 'orange', width: 6 }
	};
	const newLinkNext = {
		source: newNode.id.toString(),
		target: nextNode.id.toString(),
		lineStyle: { color: 'orange', width: 6 }
	};
	// 4. 修改边
	// 插入新节点指向
	graphData.value.links.splice(insertIndex + 1, 0, newLinkNext);
	myChart.value.setOption({ series: [{ links: graphData.value.links }] });
	await sleep(searchSpeed.value); // 延迟动画
	// 删除重复边
	// 删除边变红
	graphData.value.links.forEach((link) => {
		if (link.source == prevNode.id.toString() && link.target == nextNode.id.toString()) {
			link.lineStyle = { color: 'red', width: 6 }; // 修改 lineStyle
		}
	});
	myChart.value.setOption({ series: [{ links: graphData.value.links }] });
	await sleep(searchSpeed.value); // 延迟动画
	// 删除
	graphData.value.links = graphData.value.links.filter(
		(link) =>
			!(link.source == prevNode.id.toString() && link.target == nextNode.id.toString())
	);
	myChart.value.setOption({ series: [{ links: graphData.value.links }] });
	await sleep(searchSpeed.value); // 延迟动画
	// 插入老节点指向
	graphData.value.links.splice(insertIndex, 0, newLinkPrev);
	myChart.value.setOption({ series: [{ links: graphData.value.links }] });
	await sleep(searchSpeed.value); // 延迟动画
	// 5. 更新后续下标
	// 统一x,y轴
	graphData.value.nodes = graphData.value.nodes.map((node, index) => {
		node.x = index * 10; // 更新 x 值，按照下标 * 10
		node.y = 100; // 更新 y 值为 100
		return node;
	});
	// 获取节点总数
	const nodeCount = graphData.value.nodes.length;
	// 更新尾节点的 index (假设尾节点是最后一个节点)
	graphData.value.nodes[nodeCount - 1].index = `tail/${nodeCount - 1}`;
	// 6. 更新图表
	updateChart();
	// 重置插入数据
	insertData.value = { index: null, value: null };
};

// 移除
// 移除头节点
const headDel = async () => {
	// 确保链表不为空
	if (graphData.value.nodes.length === 0) {
		ElMessage.error('链表为空，无法移除头节点');
		return;
	}
	// 获取头节点和下一个节点（头节点的后继节点）
	const headNode = graphData.value.nodes[0];
	const nextNode = graphData.value.nodes[1];

	// 高亮头节点及其后继节点的路径
	highlightNode(headNode);
	highlightPath(headNode.id, nextNode.id);
	highligIndex(0); // 高亮头节点的索引
	// 等待动画完成
	await sleep(searchSpeed.value);

	// 移动头节点下标
	graphData.value.nodes[0].index = '';
	graphData.value.nodes[1].index = 'head/0';
	myChart.value.setOption({ series: [{ data: graphData.value.nodes }] });
	await sleep(searchSpeed.value); // 延迟动画

	// 删除头节点及其相应的链接
	graphData.value.nodes.shift(); // 删除头节点
	graphData.value.links.shift(); // 删除与头节点相关的链接

	// 更新图表
	updateChart();
	ElMessage.success('头节点已移除');
};

// 移除尾节点
const tailDel = async () => {
	// 确保链表不为空
	if (graphData.value.nodes.length === 0) {
		ElMessage.error('链表为空，无法移除尾节点');
		return;
	}

	// 遍历链表，查找目标节点
	for (let index = 0; index < graphData.value.nodes.length - 1; index++) {
		// 高亮当前节点
		await sleep(searchSpeed.value); // 动画延迟
		highlightNode(graphData.value.nodes[index]);
		highligIndex(index);
		await sleep(searchSpeed.value); // 动画延迟
		// 高亮当前路径
		highlightPath(graphData.value.nodes[index].id, graphData.value.nodes[index + 1].id);
	}
	// 获取节点总数
	const nodeCount = graphData.value.nodes.length;
	// 更新尾节点的 index (假设尾节点是最后一个节点)
	graphData.value.nodes[nodeCount - 1].index = '';
	myChart.value.setOption({ series: [{ data: graphData.value.nodes }] });
	graphData.value.nodes[nodeCount - 2].index = `tail/${nodeCount - 2}`;
	myChart.value.setOption({ series: [{ data: graphData.value.nodes }] });
	await sleep(searchSpeed.value); // 动画延迟

	// 删除尾节点及其相应的链接
	graphData.value.nodes.pop(); // 删除尾节点
	graphData.value.links.pop(); // 删除与尾节点相关的链接

	// 更新图表
	updateChart();
	ElMessage.success('尾节点已移除');
};

// 移除指定位置的节点
const Del = async () => {
	if (
		removeData.value.index === null ||
		removeData.value.index <= 0 ||
		removeData.value.index >= graphData.value.nodes.length - 1
	) {
		ElMessage.error('无效的索引');
		return;
	}
	// 节点下标，节点值
	const removeIndex = removeData.value.index;
	const nodeToRemove = graphData.value.nodes[removeIndex];

	// 遍历链表，查找目标节点
	for (let index = 0; index < removeIndex; index++) {
		// 高亮当前节点
		await sleep(searchSpeed.value); // 动画延迟
		highlightNode(graphData.value.nodes[index]);
		highligIndex(index);
		await sleep(searchSpeed.value); // 动画延迟
		// 高亮当前路径
		highlightPath(graphData.value.nodes[index].id, graphData.value.nodes[index + 1].id);
	}

	// 获取前一个和下一个节点，确保高亮路径
	const prevNode = graphData.value.nodes[removeIndex - 1] || null;
	const nextNode = graphData.value.nodes[removeIndex + 1] || null;

	// 删除点标记
	graphData.value.nodes[removeIndex].index = `del/${removeIndex}`;
	graphData.value.nodes[removeIndex].y = 90;
	graphData.value.nodes[removeIndex].itemStyle.color = 'red';
	graphData.value.nodes[removeIndex].itemStyle.shadowColor = 'red';
	graphData.value.nodes[removeIndex].itemStyle.shadowBlur = 40;
	myChart.value.setOption({ series: [{ data: graphData.value.nodes }] });
	await sleep(searchSpeed.value); // 动画延迟

	// 删除指定位置的节点及其相应的链接
	if (prevNode) {
		// 移除前一节点到该节点的链接
		graphData.value.links = graphData.value.links.filter(
			(link) =>
				!(
					link.source == prevNode.id.toString() &&
					link.target == nodeToRemove.id.toString()
				)
		);
	}
	myChart.value.setOption({ series: [{ links: graphData.value.links }] });
	await sleep(searchSpeed.value); // 延迟动画

	// 添加新边
	const newLinkNext = {
		source: prevNode.id.toString(),
		target: nextNode.id.toString(),
		lineStyle: { color: 'orange', width: 6 }
	};
	graphData.value.links.splice(removeIndex - 1, 0, newLinkNext);
	myChart.value.setOption({ series: [{ links: graphData.value.links }] });
	await sleep(searchSpeed.value); // 延迟动画

	// 移除删除节点和边
	if (nextNode) {
		// 移除该节点到下一节点的链接
		graphData.value.links = graphData.value.links.filter(
			(link) =>
				!(
					link.source == nodeToRemove.id.toString() &&
					link.target == nextNode.id.toString()
				)
		);
	}
	// 删除节点
	graphData.value.nodes.splice(removeIndex, 1);
	// console.log(graphData.value.links);
	// console.log(graphData.value.nodes);
	// 更新图表
	updateChart();
	ElMessage.success(`节点 ${nodeToRemove.name} 已移除`);
};

// 标记当前节点下标
const highligIndex = (indexId) => {
	// 创建新的节点数组以更新 index 信息
	const updatedNodes = graphData.value.nodes.map((node, index) => {
		if (index === indexId) {
			// 为节点设置新的 index 信息，标识当前节点
			if (index === 0) {
				// 头节点，更新 index 格式 'h/tmp/0'
				return { ...node, index: `h/tmp/${index}` };
			} else if (index === graphData.value.nodes.length - 1) {
				// 尾节点，更新 index 格式 't/tmp/{length-1}'
				return { ...node, index: `t/tmp/${index}` };
			} else {
				// 中间节点，更新 index
				return { ...node, index: `tmp/${indexId}` };
			}
		}
		// 其他节点保持原来的 index，头尾节点不修改
		if (index === 0 && indexId !== 0) {
			return { ...node, index: `head/${index}` }; // 头节点
		} else if (
			index === graphData.value.nodes.length - 1 &&
			indexId !== graphData.value.nodes.length - 1
		) {
			return { ...node, index: `tail/${index}` }; // 尾节点
		}
		return { ...node, index: '' }; // 清空其他节点的 index
	});

	// 更新图表显示
	myChart.value.setOption({ series: [{ data: updatedNodes }] });
};

// 高亮当前节点
const highlightNode = (currentNode) => {
	graphData.value.nodes = graphData.value.nodes.map((node) => ({
		...node,
		itemStyle: {
			...node.itemStyle,
			color: currentNode === node ? 'red' : node.itemStyle.color, // 高亮当前节点
			shadowColor: 'red',
			shadowBlur: currentNode === node ? 40 : node.itemStyle.shadowBlur // 只高亮当前节点
		}
	}));
	// 更新图表显示
	myChart.value.setOption({ series: [{ data: graphData.value.nodes }] });
};

// 高亮路径
const highlightPath = (sourceId, targetId) => {
	graphData.value.links = graphData.value.links.map((link) => ({
		...link,
		lineStyle: {
			color:
				link.source == sourceId && link.target == targetId
					? 'orange'
					: link.lineStyle.color,
			width:
				link.source == sourceId && link.target == targetId ? 6 : link.lineStyle.width
		}
	}));
	// 更新图表显示
	myChart.value.setOption({ series: [{ links: graphData.value.links }] });
};

// 睡眠函数
const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, 5000 / ms));

// 重置图表
const updateChart = () => {
	graphData.value.nodes = graphData.value.nodes.map((node) => ({
		...node,
		itemStyle: {
			...node.itemStyle,
			color: 'black',
			shadowColor: 'red',
			shadowBlur: 0
		}
	}));
	graphData.value.links = graphData.value.links.map((link) => ({
		...link,
		lineStyle: {
			color: 'black',
			width: '2'
		}
	}));
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
			updateChart();
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
.Graphs {
	width: 100%;
	height: 80vh;
	display: flex;

	.operate-btn {
		flex: 20%;
		margin-top: 1.25rem;
		padding: 0.625rem;

		.operate-btn-t {
			width: 100%;
			height: 100%;

			.btn_operation {
				width: 12rem;
				margin: 0.3125rem auto;
			}

			h3 {
				width: 12rem;
				font-size: 1.2rem;
				text-align: center;
			}
		}
	}

	.chart-container {
		flex: 80%;
		margin-top: 1.25rem;
		background-image: radial-gradient(circle, #e5e7eb 1px, transparent 1px);
	}
}
</style>
