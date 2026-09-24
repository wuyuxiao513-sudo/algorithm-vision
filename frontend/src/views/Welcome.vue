<template>
	<div class="welcome-container">
		<!-- 英雄区域 -->
		<section class="hero-section">
			<div class="hero-content">
				<h1 class="hero-title">算法视界</h1>
				<p class="hero-subtitle">数据结构与算法可视化学习平台</p>
				<p class="hero-description">
					通过直观的可视化演示，深入理解数据结构和算法的运行原理
				</p>
				<div class="hero-actions">
					<el-button type="primary" size="large" @click="goToAlgorithms">
						<el-icon>
							<View />
						</el-icon>
						开始探索
					</el-button>
					<el-button size="large" @click="scrollToFeatures">
						<el-icon>
							<InfoFilled />
						</el-icon>
						了解更多
					</el-button>
				</div>
			</div>
			<div class="hero-visual">
				<div class="algorithm-animation">
					<div class="node-animation">
						<div class="node" v-for="i in 6" :key="i" :style="{ animationDelay: `${i * 0.2}s` }"></div>
					</div>
				</div>
			</div>
		</section>

		<!-- 项目介绍 -->
		<section class="intro-section">
			<div class="container">
				<h2 class="section-title">项目简介</h2>
				<div class="intro-grid">
					<div class="intro-card">
						<div class="intro-icon">
							<el-icon>
								<DataAnalysis />
							</el-icon>
						</div>
						<h3>可视化学习</h3>
						<p>通过动态演示和交互式操作，直观展示数据结构和算法的执行过程</p>
					</div>
					<div class="intro-card">
						<div class="intro-icon">
							<el-icon>
								<Cpu />
							</el-icon>
						</div>
						<h3>丰富内容</h3>
						<p>涵盖排序算法、图算法、数据结构等多种经典算法和数据结构</p>
					</div>
					<div class="intro-card">
						<div class="intro-icon">
							<el-icon>
								<User />
							</el-icon>
						</div>
						<h3>用户友好</h3>
						<p>简洁的界面设计，支持逐步执行、速度调节等个性化学习体验</p>
					</div>
				</div>
			</div>
		</section>

		<!-- 功能特性 -->
		<section class="features-section" ref="featuresSection">
			<div class="container">
				<h2 class="section-title">核心功能</h2>
				<div class="features-grid">
					<div class="feature-card">
						<div class="feature-image">
							<el-icon>
								<Sort />
							</el-icon>
						</div>
						<div class="feature-content">
							<h3>排序算法</h3>
							<ul>
								<li>冒泡排序</li>
								<li>快速排序</li>
								<li>插入排序</li>
								<li>选择排序</li>
							</ul>
							<p>支持可视化比较和交换过程，理解不同排序算法的效率差异</p>
						</div>
					</div>
					<div class="feature-card">
						<div class="feature-image">
							<el-icon>
								<Connection />
							</el-icon>
						</div>
						<div class="feature-content">
							<h3>图算法</h3>
							<ul>
								<li>广度优先搜索(BFS)</li>
								<li>深度优先搜索(DFS)</li>
								<li>Dijkstra最短路径</li>
							</ul>
							<p>动态展示图的遍历过程和最短路径查找算法</p>
						</div>
					</div>
					<div class="feature-card">
						<div class="feature-image">
							<el-icon>
								<SetUp />
							</el-icon>
						</div>
						<div class="feature-content">
							<h3>数据结构</h3>
							<ul>
								<li>栈(Stack)</li>
								<li>队列(Queue)</li>
								<li>链表(LinkList)</li>
								<li>图结构</li>
							</ul>
							<p>可视化展示各种数据结构的操作和内部实现</p>
						</div>
					</div>
				</div>
			</div>
		</section>

		<!-- 技术栈 -->
		<section class="tech-section">
			<div class="container">
				<h2 class="section-title">技术栈</h2>
				<div class="tech-stack">
					<div class="tech-item">
						<div class="tech-icon vue">
							<el-icon>
								<ElementPlus />
							</el-icon>
						</div>
						<span>Vue 3 + Element Plus</span>
					</div>
					<div class="tech-item">
						<div class="tech-icon echarts">
							<el-icon>
								<TrendCharts />
							</el-icon>
						</div>
						<span>ECharts 可视化</span>
					</div>
					<div class="tech-item">
						<div class="tech-icon vite">
							<el-icon>
								<Lightning />
							</el-icon>
						</div>
						<span>Vite 构建工具</span>
					</div>
					<div class="tech-item">
						<div class="tech-icon less">
							<el-icon>
								<Brush />
							</el-icon>
						</div>
						<span>Less 样式预处理器</span>
					</div>
				</div>
			</div>
		</section>

		<!-- 快速入口 -->
		<section class="quick-access-section">
			<div class="container">
				<h2 class="section-title">快速开始</h2>
				<div class="access-grid">
					<div class="access-card" @click="goToSorting">
						<div class="access-icon">
							<el-icon>
								<Sort />
							</el-icon>
						</div>
						<h3>排序算法</h3>
						<p>探索各种排序算法的可视化演示</p>
						<el-button type="primary" text>立即体验</el-button>
					</div>
					<div class="access-card" @click="goToGraph">
						<div class="access-icon">
							<el-icon>
								<Connection />
							</el-icon>
						</div>
						<h3>图算法</h3>
						<p>学习图的遍历和最短路径算法</p>
						<el-button type="primary" text>立即体验</el-button>
					</div>
					<div class="access-card" @click="goToDataStructures">
						<div class="access-icon">
							<el-icon>
								<SetUp />
							</el-icon>
						</div>
						<h3>数据结构</h3>
						<p>理解各种数据结构的操作原理</p>
						<el-button type="primary" text>立即体验</el-button>
					</div>
				</div>
			</div>
		</section>

		<!-- 底部信息 -->
		<footer class="footer-section">
			<div class="container">
				<div class="footer-content">
					<div class="footer-info">
						<h3>算法视界</h3>
						<p>让算法学习变得直观易懂</p>
					</div>
					<div class="footer-links">
						<a href="#" @click="goToAlgorithms">算法演示</a>
						<a href="#" @click="goToArticles">技术文章</a>
						<a href="#" @click="goToAIChat" disabled="true">AI助手</a>
					</div>
				</div>
				<div class="footer-bottom">
					<p>&copy; 2025 算法视界 - 数据结构与算法可视化平台</p>
				</div>
			</div>
		</footer>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import {
	Brush,
	Connection,
	Cpu,
	DataAnalysis,
	ElementPlus,
	InfoFilled,
	Lightning,
	SetUp,
	Sort,
	TrendCharts,
	User,
	View
} from '@element-plus/icons-vue';

const router = useRouter();
const featuresSection = ref(null);

// 导航方法
const goToAlgorithms = () => {
	router.push('/algorithms');
};

const goToSorting = () => {
	router.push('/algorithms?category=sort');
};

const goToGraph = () => {
	router.push('/algorithms?category=graph');
};

const goToDataStructures = () => {
	router.push('/algorithms?category=data-structure');
};

const goToArticles = () => {
	router.push('/article');
};

const goToAIChat = () => {
	router.push('/aichat');
};

const scrollToFeatures = () => {
	featuresSection.value?.scrollIntoView({ behavior: 'smooth' });
};
</script>

<style lang="less" scoped>
.welcome-container {
	min-height: 100vh;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

// 英雄区域
.hero-section {
	display: flex;
	align-items: center;
	justify-content: space-between;
	min-height: 80vh;
	padding: 80px 5%;
	color: white;

	.hero-content {
		flex: 1;
		max-width: 600px;

		.hero-title {
			font-size: 4rem;
			font-weight: 700;
			margin-bottom: 1rem;
			background: linear-gradient(45deg, #fff, #e0e7ff);
			-webkit-background-clip: text;
			-webkit-text-fill-color: transparent;
		}

		.hero-subtitle {
			font-size: 1.5rem;
			margin-bottom: 1.5rem;
			opacity: 0.9;
		}

		.hero-description {
			font-size: 1.1rem;
			margin-bottom: 2.5rem;
			opacity: 0.8;
			line-height: 1.6;
		}

		.hero-actions {
			display: flex;
			gap: 1rem;

			.el-button {
				padding: 12px 24px;
				font-size: 1rem;
			}
		}
	}

	.hero-visual {
		flex: 1;
		display: flex;
		justify-content: center;
		align-items: center;

		.algorithm-animation {
			.node-animation {
				display: flex;
				gap: 20px;

				.node {
					width: 60px;
					height: 60px;
					background: rgba(255, 255, 255, 0.1);
					border: 2px solid rgba(255, 255, 255, 0.3);
					border-radius: 50%;
					animation: float 3s ease-in-out infinite;

					@keyframes float {
						0%, 100% {
							transform: translateY(0px);
						}
						50% {
							transform: translateY(-20px);
						}
					}
				}
			}
		}
	}
}

// 通用容器
.container {
	max-width: 1200px;
	margin: 0 auto;
	padding: 0 20px;
}

// 章节通用样式
.section-title {
	text-align: center;
	font-size: 2.5rem;
	font-weight: 600;
	margin-bottom: 3rem;
	color: #2d3748;
}

// 项目介绍区域
.intro-section {
	background: white;
	padding: 80px 0;

	.intro-grid {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
		gap: 2rem;

		.intro-card {
			text-align: center;
			padding: 2rem;
			border-radius: 12px;
			background: #f8fafc;
			transition: transform 0.3s ease;

			&:hover {
				transform: translateY(-5px);
			}

			.intro-icon {
				width: 80px;
				height: 80px;
				margin: 0 auto 1rem;
				background: linear-gradient(135deg, #667eea, #764ba2);
				border-radius: 50%;
				display: flex;
				align-items: center;
				justify-content: center;

				.el-icon {
					font-size: 2rem;
					color: white;
				}
			}

			h3 {
				font-size: 1.5rem;
				margin-bottom: 1rem;
				color: #2d3748;
			}

			p {
				color: #718096;
				line-height: 1.6;
			}
		}
	}
}

// 功能特性区域
.features-section {
	background: #f7fafc;
	padding: 80px 0;

	.features-grid {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
		gap: 2rem;

		.feature-card {
			background: white;
			border-radius: 12px;
			overflow: hidden;
			box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
			transition: transform 0.3s ease;

			&:hover {
				transform: translateY(-5px);
			}

			.feature-image {
				height: 200px;
				background: linear-gradient(135deg, #667eea, #764ba2);
				display: flex;
				align-items: center;
				justify-content: center;
				color: white;
				font-size: 3rem;
			}

			.feature-content {
				padding: 2rem;

				h3 {
					font-size: 1.5rem;
					margin-bottom: 1rem;
					color: #2d3748;
				}

				ul {
					margin-bottom: 1rem;

					li {
						color: #718096;
						margin-bottom: 0.5rem;
					}
				}

				p {
					color: #718096;
					line-height: 1.6;
				}
			}
		}
	}
}

// 技术栈区域
.tech-section {
	background: white;
	padding: 80px 0;

	.tech-stack {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
		gap: 2rem;

		.tech-item {
			display: flex;
			align-items: center;
			gap: 1rem;
			padding: 1.5rem;
			background: #f8fafc;
			border-radius: 8px;
			transition: transform 0.3s ease;

			&:hover {
				transform: translateX(5px);
			}

			.tech-icon {
				width: 60px;
				height: 60px;
				border-radius: 50%;
				display: flex;
				align-items: center;
				justify-content: center;

				&.vue {
					background: #42b883;
				}

				&.echarts {
					background: #aa4643;
				}

				&.vite {
					background: #646cff;
				}

				&.less {
					background: #1d365d;
				}

				.el-icon {
					font-size: 1.5rem;
					color: white;
				}
			}

			span {
				font-size: 1.1rem;
				font-weight: 500;
				color: #2d3748;
			}
		}
	}
}

// 快速入口区域
.quick-access-section {
	background: #f7fafc;
	padding: 80px 0;

	.access-grid {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
		gap: 2rem;

		.access-card {
			background: white;
			padding: 2rem;
			border-radius: 12px;
			text-align: center;
			cursor: pointer;
			transition: all 0.3s ease;
			border: 2px solid transparent;

			&:hover {
				border-color: #667eea;
				transform: translateY(-5px);
			}

			.access-icon {
				width: 80px;
				height: 80px;
				margin: 0 auto 1rem;
				background: linear-gradient(135deg, #667eea, #764ba2);
				border-radius: 50%;
				display: flex;
				align-items: center;
				justify-content: center;

				.el-icon {
					font-size: 2rem;
					color: white;
				}
			}

			h3 {
				font-size: 1.5rem;
				margin-bottom: 1rem;
				color: #2d3748;
			}

			p {
				color: #718096;
				margin-bottom: 1.5rem;
				line-height: 1.6;
			}
		}
	}
}

// 底部区域
.footer-section {
	background: #2d3748;
	color: white;
	padding: 60px 0 20px;

	.footer-content {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 2rem;

		.footer-info h3 {
			font-size: 1.5rem;
			margin-bottom: 0.5rem;
		}

		.footer-links {
			display: flex;
			gap: 2rem;

			a {
				color: #cbd5e0;
				text-decoration: none;
				transition: color 0.3s ease;

				&:hover {
					color: white;
				}
			}
		}
	}

	.footer-bottom {
		text-align: center;
		padding-top: 2rem;
		border-top: 1px solid #4a5568;
		color: #a0aec0;
	}
}

// 响应式设计
@media (max-width: 768px) {
	.hero-section {
		flex-direction: column;
		text-align: center;
		padding: 60px 5%;

		.hero-title {
			font-size: 2.5rem;
		}

		.hero-actions {
			justify-content: center;
		}

		.hero-visual {
			margin-top: 2rem;
		}
	}

	.section-title {
		font-size: 2rem;
	}

	.footer-content {
		flex-direction: column;
		gap: 2rem;
		text-align: center;
	}
}
</style>