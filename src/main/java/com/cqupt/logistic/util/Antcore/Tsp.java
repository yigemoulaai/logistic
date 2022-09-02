package com.cqupt.logistic.util.Antcore;

public class Tsp {
	
	//蚂蚁数组  
    public Ant[] m_antAry=new Ant[PublicFun.N_ANT_COUNT];  
    public Ant[] m_betterAnts=new Ant[PublicFun.N_IT_COUNT];//定义一组蚂蚁,用来保存每一次搜索中较优结果,不参与搜索  
    public Ant m_bestAnt;//定义一个蚂蚁变量,用来保存最终最优结果,不参与搜索  
    
	/* 
     * 初始化数据 
     */  
    public void InitData() throws CloneNotSupportedException  
    {  
        //初始化所有蚂蚁  
          
        PublicFun.g_Trial=new double[PublicFun.N_CITY_COUNT][PublicFun.N_CITY_COUNT];  
        m_bestAnt=new Ant();  
        for (int i = 0; i <PublicFun.N_ANT_COUNT; i++)  
        {  
            m_antAry[i]=new Ant();  
        }  
        for (int i = 0; i < PublicFun.N_IT_COUNT; i++)  
        {  
            m_betterAnts[i]=new Ant();  
            m_betterAnts[i].m_dbPathLength=PublicFun.DB_MAX;//把较优蚂蚁的路径长度设置为一个很大值  
        }  
          
        //先把最优蚂蚁的路径长度设置为一个很大值  
        m_bestAnt.m_dbPathLength=PublicFun.DB_MAX;  
          
        //初始化信息素  
        for(int i=0;i<PublicFun.N_CITY_COUNT;i++)  
        {  
            for(int j=0;j<PublicFun.N_CITY_COUNT;j++)  
            {  
                PublicFun.g_Trial[i][j]=1.0;  
            }  
        }  
          
        Iterator();//开始迭代  
          
    }  
      
    /* 
     * 更新环境信息素 
     */  
      
    public void updateTrial()
    {  
        //临时数组,保存各只蚂蚁在两两城市间新留下的信息素  
        double[][] dbTempAry=new double[PublicFun.N_CITY_COUNT][PublicFun.N_CITY_COUNT];  
          
        //全部设置为0;  
        for (int i = 0; i <PublicFun.N_CITY_COUNT; i++)  
        {  
            for (int j = 0; j < PublicFun.N_CITY_COUNT; j++)  
            {  
                dbTempAry[i][j]=0.0;  
            }  
        }  
        //计算新增加的信息素,保存到临时变量  
        int m=0;  
        int n=0;  
        for(int i=0; i<PublicFun.N_ANT_COUNT;i++)  
        {  
            for (int j = 1; j < PublicFun.N_CITY_COUNT; j++)  
            {  
                m=m_antAry[i].m_nPath[j];  
                n=m_antAry[i].m_nPath[j-1];  
                dbTempAry[n][m]=dbTempAry[n][m]+PublicFun.DBQ/m_antAry[i].m_dbPathLength;  
                dbTempAry[m][n]=dbTempAry[n][m];  
            }  
              
            //最后城市与开始城市之间的信息素  
            n=m_antAry[i].m_nPath[0];  
            dbTempAry[n][m]=dbTempAry[n][m]+PublicFun.DBQ/m_antAry[i].m_dbPathLength;  
            dbTempAry[m][n]=dbTempAry[n][m];  
        }  
          
        //更新环境信息素  
        for (int i = 0; i < PublicFun.N_CITY_COUNT; i++)  
        {  
            for (int j = 0; j < PublicFun.N_CITY_COUNT; j++)  
            {  
                //最新的环境信息素 = 留存的信息素 + 新留下的信息素    
                PublicFun.g_Trial[i][j]=PublicFun.g_Trial[i][j]*PublicFun.ROU+dbTempAry[i][j];  
            }  
        }  
    }  
      
      
    /* 
     * 迭代 
     */  
    public void Iterator() throws CloneNotSupportedException  
    {  
        //迭代次数内进行循环  
  
        for (int i = 0; i < PublicFun.N_IT_COUNT; i++)  
        {  
            //每只蚂蚁搜索一遍  
            for(int j=0;j<PublicFun.N_ANT_COUNT;j++)  
            {  
                m_antAry[j].Search();  
            }  
              
            //保存较优结果  
            for(int j=0;j<PublicFun.N_ANT_COUNT;j++)  
            {  
                if (m_antAry[j].m_dbPathLength < m_betterAnts[i].m_dbPathLength)  
                {  
                    m_betterAnts[i] = (Ant) m_antAry[j].clone();  
                }  
            }  
           updateTrial();
        }  
  
        //找出最优蚂蚁  
        for(int k=0;k<PublicFun.N_IT_COUNT;k++)  
        {  
            if(m_betterAnts[k].m_dbPathLength<m_bestAnt.m_dbPathLength)  
            {  
                m_bestAnt=m_betterAnts[k];  
            }  
        }  
          
      getBetterAnt();//输出每次的较优路径
      getBestAnt();//输出最佳路径
          
    }  
    /* 
     * 输出最佳路径到控制台.(暂不使用,但保留) 
     */  
    public void getBestAnt()  
    {  
        System.out.println("最佳路径:");  
        System.out.println( "路径:"+getAntPath(m_bestAnt)+"长度:"+getAntLength(m_bestAnt));  
    }  
      
    /* 
     * 输出每次的较优路径到控制台.(暂不使用,但保留) 
     */  
    public void getBetterAnt()  
    {  
        System.out.println("每一次的较优路径:");  
        for (int i = 0; i < m_betterAnts.length; i++)  
        {  
             System.out.println("("+i+") 路径:"+getAntPath(m_betterAnts[i])+"长度:"+getAntLength(m_betterAnts[i]));  
        }  
    }  
  
    /* 
     * 返回蚂蚁经过的路径 
     */  
    public String getAntPath(Ant ant)  
    {
        String s="";  
        for(int i=0;i<ant.m_nPath.length;i++)  
        {  
            s+=ant.m_nPath[i]+"-";  
        }  
        s+=ant.m_nPath[0];  //蚂蚁最后要回到开始城市  
        return s;  
    }  
    /* 
     * 返回蚂蚁经过的长度 
     */  
    public double getAntLength(Ant ant)  
    {  
        return ant.m_dbPathLength;  
    }


    public static void main(String[] args) throws CloneNotSupportedException {
        Double[][] g_Distance = new Double[][]{{0d,2d},{1d,2d},{1d,2d},{1d,2d},{1d,2d},{1d,2d},{1d,2d},{1d,2d}};
        PublicFun.g_Distance = g_Distance;
        // 城市数量
        PublicFun.N_CITY_COUNT = 5;
        //初始化蚂蚁数量
        PublicFun.N_ANT_COUNT = 2*5;
        Tsp tsp = new Tsp();
        tsp.InitData();
        Ant ant = tsp.m_bestAnt;


    }

}
