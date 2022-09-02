package com.cqupt.logistic.util.Antcore;

public class Ant implements Cloneable {

	public int[] m_nPath = new int[PublicFun.N_CITY_COUNT];// 蚂蚁走过的路径
	public double m_dbPathLength;// 蚂蚁走过的路径长度
	public int[] m_nAllowedCity = new int[PublicFun.N_CITY_COUNT];// 蚂蚁没有去过的城市
	public int m_nCurCityNo;// 当前所在城市的编号
	public int m_nMovedCityCount;// 已经去过的城市数量
	/*
	 * 初始化函数,蚂蚁搜索前调用该方法
	 */
	public void Init() {
		for (int i = 0; i < PublicFun.N_CITY_COUNT; i++) {
			m_nAllowedCity[i] = 1;// 设置全部城市没有去过
			m_nPath[i] = 0;// 蚂蚁走过的路径全部设置为0
		}
		m_dbPathLength = 0.0; // 蚂蚁走过的路径长度设置为0

		m_nCurCityNo = 0;//选择一个出发点

		m_nPath[0] = m_nCurCityNo;// 把出发城市保存的路径数组中

		m_nAllowedCity[m_nCurCityNo] = 0;// 标识出发城市已经去过了

		m_nMovedCityCount = 1;// 已经去过的城市设置为1;
	}

	/*
	 * 覆盖Object中的clone()方法 实现对象的复制
	 */
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/*
	 * 选择下一个城市 返回值为城市编号
	 */
	public int ChooseNextCity() {

		int nSelectedCity = -1;// 返回结果,初始化为-1

		// 计算当前城市和没去过城市的信息素的总和
		double dbTotal = 0.0;
		double[] prob = new double[PublicFun.N_CITY_COUNT];// 用来保存各个城市被选中的概率

		for (int i = 0; i < PublicFun.N_CITY_COUNT; i++) {
			if (m_nAllowedCity[i] == 1)// 城市没去过
			{
				// 该城市和当前城市的信息素
				prob[i] = Math.pow(PublicFun.g_Trial[m_nCurCityNo][i], PublicFun.ALPHA)
						* Math.pow(1.0 / PublicFun.g_Distance[m_nCurCityNo][i], PublicFun.BETA);
				dbTotal = dbTotal + prob[i];// 累加信息素
			} else// 如果城市去过了 则被选中的概率为0;
			{
				prob[i] = 0.0;
			}

		}

		// 进行轮盘选择

		double dbTemp = 0.0;
		if (dbTotal > 0.0)// 如果总的信息素大于0
		{
			dbTemp = PublicFun.rnd(0.0, dbTotal);// 取一个随机数

			for (int i = 0; i < PublicFun.N_CITY_COUNT; i++) {
				if (m_nAllowedCity[i] == 1)// 城市没有去过
				{
					dbTemp = dbTemp - prob[i];// 相当于轮盘

					if (dbTemp < 0.0)// 轮盘停止转动,记下城市编号,跳出循环
					{
						nSelectedCity = i;
						break;
					}
				}

			}
		}

		/*
		 * 如果城市间的信息素非常小 ( 小到比double能够表示的最小的数字还要小 ) 那么由于浮点运算的误差原因，上面计算的概率总和可能为0
		 * 会出现经过上述操作，没有城市被选择出来 出现这种情况，就把第一个没去过的城市作为返回结果
		 */
		if (nSelectedCity == -1) {
			for (int i = 0; i < PublicFun.N_CITY_COUNT; i++) {
				if (m_nAllowedCity[i] == 1)// 城市没有去过
				{
					nSelectedCity = i;
					break;
				}
			}
		}
		return nSelectedCity;
	}

	/*
	 * 蚂蚁在城市间移动
	 */
	public void Move() {
		int nCityNo = ChooseNextCity();// 选择下一个城市
		m_nPath[m_nMovedCityCount] = nCityNo;// 保存蚂蚁走过的路径
		m_nAllowedCity[nCityNo] = 0;// 把这个城市设置为已经去过了
		m_nCurCityNo = nCityNo;// 改变当前城市为选择的城市
		m_nMovedCityCount++;// 已经去过的城市加1
	}

	/*
	 * 蚂蚁进行一次搜索
	 */
	public void Search() {
		Init();// 蚂蚁搜索前,进行初始化

		// 如果蚂蚁去过的城市数量小于城市数量,就继续移动
		while (m_nMovedCityCount < PublicFun.N_CITY_COUNT) {
			Move();// 移动
		}
		// 完成搜索后计算走过的路径长度
		CalPathLength();
	}

	/*
	 * 计算蚂蚁走过的路径长度
	 */
	public void CalPathLength() {
		m_dbPathLength = 0.0;// 先把路径长度置0
		int m = 0;
		int n = 0;

		for (int i = 1; i < PublicFun.N_CITY_COUNT; i++) {
			m = m_nPath[i];
			n = m_nPath[i - 1];
			m_dbPathLength = m_dbPathLength + PublicFun.g_Distance[m][n];
		}
		// 加上从最后城市返回出发城市的距离
		n = m_nPath[0];
		m_dbPathLength = m_dbPathLength + PublicFun.g_Distance[m][n];
		m_dbPathLength = (Math.round(m_dbPathLength * 100)) / 100.0;
	}

}
