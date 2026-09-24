import { createRouter, createWebHistory } from 'vue-router';
import Main from '../views/Main.vue';
import Welcome from '../views/Welcome.vue';
import Home from '../views/Home.vue';
import MySpace from '../views/User/MySpace.vue';
import PersonalCenter from '../views/User/PersonalCenter.vue';
import Register from '../views/User/Register.vue';
import ContentView from '../views/PostList/ContentView.vue';
import PublishView from '../views/PostList/PublishView.vue';
import Article from '../views/Article.vue';
import Algorithms from '../views/Algorithms.vue';
import Exercises from '../views/Exercises.vue';
import Bubbling_sort from '../views/GraphSf/sort/Bubbling_sort.vue';
import Selection_sort from '../views/GraphSf/sort/Selection_sort.vue';
import Insertion_sort from '../views/GraphSf/sort/Insertion_sort.vue';
import Quick_sort from '../views/GraphSf/sort/quick_sort.vue';
import BFS from '../views/GraphSf/graph/BFS.vue';
import BfsGrid from '../views/GraphSf/graph/BfsGrid.vue';
import DfsGrid from '../views/GraphSf/graph/DfsGrid.vue';
import Dijkstra from '../views/GraphSf/graph/Dijkstra.vue';
import LinkList from '../views/GraphSf/dataStructure/LinkList.vue';
import Stack from '../views/GraphSf/dataStructure/Stack.vue';
import Queue from '../views/GraphSf/dataStructure/Queue.vue';
import GraphStructures from '../views/GraphSf/dataStructure/GraphStructures.vue';
import Test from '../views/Test/Test.vue';
import Cookie from 'js-cookie';
import { useUserStore } from '../store/user';
import Search from '../views/Search.vue';
import Refresh from '../views/Refresh.vue';
import Analyse from '../views/Analyse.vue';

const routes = [
	{
		path: '/',
		name: 'Main',
		component: Main,
		redirect: '/welcome', //重定向
		children: [
			{
				path: '/Test',
				name: 'Test',
				component: Test,
				meta: { title: 'Test' }
			},
			{
				path: 'welcome',
				name: 'Welcome',
				component: Welcome,
				meta: { title: '欢迎页' }
			}, //欢迎页
			{
				path: 'home',
				name: 'Home',
				component: Home,
				meta: { title: '首页' }
			}, //首页
			{
				path: 'search',
				name: 'Search',
				component: Search,
				meta: {
					title: '搜索结果',
					keepAlive: true
				}
			},
			{
				path: 'article',
				name: 'Article',
				component: Article,
				meta: { title: '文章管理' }
			}, //基础课程
			{
				path: 'content',
				name: 'ContentView',
				component: ContentView,
				meta: { title: '文章' }
			}, //文章
			{
				path: 'myspace',
				name: 'MySpace',
				component: MySpace,
				meta: { title: '我的个人空间' }
			}, //我的个人空间
			{
				path: 'personalCenter',
				name: 'PersonalCenter',
				component: PersonalCenter,
				meta: { title: '个人中心' }
			}, //个人中心
			{
				path: 'algorithms',
				name: 'Algorithms',
				component: Algorithms,
				meta: { title: '算法' }
			}, //算法
			{
				path: 'analyse',
				name: 'Analyse',
				component: Analyse,
				meta: { title: '算法分析' }
			}, //算法分析
				{
				path: 'exercises',
				name: 'Exercises',
				component: Exercises,
				meta: { title: '算法练习' }
			}, //算法练习
			{
				path: 'bubbling_sort',
				name: 'Bubbling_sort',
				component: Bubbling_sort,
				meta: { title: '冒泡排序' }
			}, //冒泡排序
			{
				path: 'selection_sort',
				name: 'Selection_sort',
				component: Selection_sort,
				meta: { title: '选择排序' }
			}, //选择排序
			{
				path: 'insertion_sort',
				name: 'Insertion_sort',
				component: Insertion_sort,
				meta: { title: '插入排序' }
			}, //插入排序
			{
				path: 'quick_sort',
				name: 'Quick_sort',
				component: Quick_sort,
				meta: { title: '快速排序' }
			}, //快速排序
			{
				path: 'BFS',
				name: 'BFS',
				component: BFS,
				meta: { title: 'BFS' }
			}, //BFS
			{
				path: 'bfsGrid',
				name: 'BfsGrid',
				component: BfsGrid,
				meta: { title: 'BfsGrid' }
			}, //BfsGrid
			{
				path: 'dfsGrid',
				name: 'DfsGrid',
				component: DfsGrid,
				meta: { title: 'DfsGrid' }
			}, //DfsGrid
			{
				path: 'dijkstra',
				name: 'Dijkstra',
				component: Dijkstra,
				meta: { title: 'Dijkstra' }
			}, //Dijkstra
			{
				path: 'linklist',
				name: 'LinkList',
				component: LinkList,
				meta: { title: '链表' }
			}, //LinkList
			{
				path: 'stack',
				name: 'Stack',
				component: Stack,
				meta: { title: '栈' }
			}, //Stack
			{
				path: 'queue',
				name: 'Queue',
				component: Queue,
				meta: { title: '队列' }
			}, //Queue
			{
				path: 'graphStructures',
				name: 'GraphStructures',
				component: GraphStructures,
				meta: { title: '图结构' }
			} //GraphStructures
		]
	},
	{
		path: '/register',
		name: 'Register',
		component: Register,
		meta: { title: '注册' }
	},
	{
		path: '/refresh',
		name: 'Refresh',
		component: Refresh,
		meta: {
			title: '登录成功',
			requiresAuth: true // 表示需要登录才能访问
		}
	}, //注册
	{
		path: '/publish',
		name: 'PublishView',
		component: PublishView,
		meta: { title: '发布' }
	} //发布文章
];

const router = createRouter({
	history: createWebHistory(),
	routes
});

router.beforeEach((to, from, next) => {
	// 获取 user 的 Pinia
	const userStore = useUserStore();
	let token = Cookie.get('token');

	// 判断是否是特定路由，如果是，则无论 token 是否存在都放行
	const allowedRoutes = [
		'Test',
		'Register',
		'Welcome',
		'Home',
		'Algorithms',
		'ContentView',
		'Bubbling_sort',
		'Selection_sort',
		'Insertion_sort',
		'Quick_sort',
		'BFS',
		'BfsGrid',
		'DfsGrid',
		'Dijkstra',
		'LinkList',
		'Stack',
		'Queue'
	];

	if (allowedRoutes.includes(to.name)) {
		// 如果是这些路由，直接放行
		window.document.title = to.meta.title;
		next();
	} else {
		// 如果 token 不存在，跳转到 welcome 页面，并显示登录弹窗
		if (!token) {
			userStore.setuserData({});
			userStore.setCenterDialogVisible(true);
			userStore.editLoginOrNot(false);
			next({ name: 'Welcome' }); // 跳转到 welcome 页面
			setTimeout(() => {
				userStore.setSelectedIndex(0);
			}, 1);
		} else {
			// 如果有 token，正常放行
			window.document.title = to.meta.title;
			next();
		}
	}
});

export default router;
