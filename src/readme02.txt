1.保存作用域
  原始情况下：保存作用域我们可认为有四个：
            ①page（页面级别，基本不用）
            ②request（一次请求响应的范围）
            ③session（一次会话范围）
            ④application（整个应用程序范围）:
                         可以在不同的浏览器下，不需在同一session下，也可以访问保存作用域

2.路径问题
  相对路径：是该文件到要查找文件之间的路径
          例 ../ 是返回上一级目录

  绝对路径：是要查找文件从根目录开始，到该文件的路径

今日内容：
1.再次学习servlet的初始化方法
2.学习servlet中的ServletContext和<context-param>
3.什么是业务层
4.IOC
      1）耦合/依赖
      依赖指的是某某某离不开某某
      在软件系统中，层与层之间是存在依赖的。我们也称之为耦合。
      我们系统架构或是设计的一个原则是：高内聚低耦合。
      层内部的组成应该是高度聚合的，而层与层之间的关系应该是低耦合的，最理想的情况0耦合（就是没有耦合）
      2）IOC -控制反转 /DI -依赖注入

5.过滤器Filter
1）Filter也属于Servlet规范
2）Filter开发步骤：新建类实现Filter接口，然后实现其中的三个方法：init、doFilter、destroy
   配置Filter，可以用注解@WebFilter，也可以使用xml文件<filter><filter-mapping>
3）Filter在配置时，和servlet一样，也可以配置通配符，例如@WebFilter("*.do")表示拦截所有以.do结尾的请求
4）过滤器链
   1）执行的顺序依次是：A B C demo02 C2 B2 A2
   2）如果采取的是注解的方式进行配置，那么过滤器链的拦截顺序是按照全类名的先后顺序排序的
   3）如果采取的是xml的方式进行配置，那么按照配置的先后顺序进行排序


6.事务管理
   1）涉及到组件：
      -OpenSessionInViewFilter
      -TransactionManager
      -ThreadLocal
      -ConnUtil
      -BaseDAO
   2）ThreadLocal
      -get(),set(obj)
      -ThreadLocal称之为本地线程。我们可以通过set方法在当前线程上存储数据、通过get方法在当前线程上获取数据
      -set方法源码分析：
          public void set(T value) {
                Thread t = Thread.currentThread();//获取当前的线程
                ThreadLocalMap map = getMap(t);   //每一个线程都维护各自的一个容器（ThreadLocalMap）
                if (map != null)
                    map.set(this, value);         //这里的key对应的是ThreadLocal，因为我们的组件中需要传输（共享）的对象可能会有多个
                else
                    createMap(t, value);          //默认情况下map是没有初始化的，那么第一次往其中添加数据时，会去初始化
            }

      -get方法源码分析
           public T get() {
                  Thread t = Thread.currentThread();//获取当前的线程
                  ThreadLocalMap map = getMap(t);   //获取和这个线程（企业）相关的ThreadLocalMap（也就是工作纽带的集合）
                  if (map != null) {
                      ThreadLocalMap.Entry e = map.getEntry(this); //this指的是ThreadLocal对象，通过它才能知道是哪一个工作纽带
                      if (e != null) {
                          @SuppressWarnings("unchecked")
                          T result = (T)e.value;   //entry.value就可以获取到工具箱了
                          return result;
                      }
                  }
                  return setInitialValue();  //map如果等于null，那就初始化
              }



7.TransActionManager、ThreadLocal、OpenSessionInViewFilter
