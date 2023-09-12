1.设置编码
1）get请求方式：
  tomcat8以后不需设置
  8之前
  1.先打散字符串成字节数组
  String fname=req.getParameter("fname");
  byte[] bytes=fname.getBytes("ISO-8859-1");
  2.将字节数组按照设定的编码重新组转成字符串
  fname=new String(bytes,"UTF-8");
2)POST请求方式：
  req.setCharacterEncoding("UTF-8");
  tomcat8之后只需针对post方式设置编码
  注意：设置编码必须在所有获取参数动作之前
2.servlet的继承关系
    1）继承关系
      javax.servlet.servlet接口
         javax.servlet.GenericServlet抽象类
                javax.servlet.http.HttpServlet抽象子类

    2)相关方法
      javax.servlet.Servlet接口:
         void init(config)  -初始化方法
         void service(request,response) -服务方法
         void destory() -销毁方法

      javax.servlet.GenericServlet抽象类:
         void service(request,response) -仍然是抽象的

      javax.servlet.http.HttpServlet抽象子类:
         void service(request,response) -不是抽象的
         1.String method=req.getMethod(); 获取请求的方式
         2.各种if判断，根据请求方式不同，决定去调用不同的do方法
         if(method.equals("GET")){

         }else if(method.equals("HEAD")){
              lastModified =this.getLastModified(req);
              this.maybeSetLastModified(resp,lastModified);
              this.doHead(req,resp);
         }else if(method.equals("POST")){
              this.doPost(req,resp);
         }...
         3.在HttpServlet这个抽象类中，do方法都差不多:
              protected void doGet(HttpServletRequest req,HttpServletResponse resp){
                  String protocol = req.getProtocol();
                  String msg = lString.getString("http.method_get_not_supported");
                  if(protocol.endsWith("1.1")){
                      resp.sendError(405,msg);
                  }else{
                      resp.sendError(400,msg);
                  }
              }
3.servlet的生命周期
   1）生命周期：从出生到死亡的过程就是生命周期，对应servlet的三个方法:init(),service(),destory()
   2）默认情况下:
      第一次接收请求时，这个servlet会进行实例化（调用构造方法）、初始化（调用init()）、然后服务（调用service()）
      从第二次请求开始，每一次都是服务
      当容器关闭时，其中所有的servlet实例会被销毁，调用销毁方法
   3）--通过实例发现:servlet实例tomcat只会创建一个,所有的请求都是这个实例去响应。默认情况下。
      --第一次请求时，tomcat才会去实例化，初始化，然后再服务。优点:可以提高系统的启动速度;缺点:第一次请求时，耗时较长
      --结论:如果需要提高系统的启动速度，当前默认情况就是这样，如果需要提高响应速度，我们应该要设置servlet的初始化时机
   4）servlet初始化时机:
       -默认是第一次接收请求时，实例化，初始化
       -我们可以通过<load-on-startup>来设置servlet启动的先后顺序,数字越小，优先级越高，最小值0
   5）servlet在容器中是:单例的（单个的实例)、线程不安全的
       -单例:所有的请求都是同一个实例去响应
       -线程不安全:一个线程需要根据这个实例中的某个成员变量值去做逻辑判断，但在中间某个时机，另一个线程改变了这个成员变量的值，从而导致第一个线程的执行路径发生了变化
       -我们已经知道了servlet是线程不安全的,启发是:尽量不要在servlet中定义成员变量，如果不得不定义成员变量，那么不要去:
                                            ①不要去修改成员变量的值
                                            ②不要根据成员变量的值去做一些逻辑判断
4.HTTP协议:超文本传输协议
   1）Http是无状态的:
      ——服务器无法判断这两次请求是同一个客户端发过来的，还是不同客户端发过来的
      ——无状态带来的现实问题:第一次请求是添加商品购物车，第二次请求是结账;如果两次请求服务器无法区分是同一个用户的，那么就会混乱
      解决方法:通过会话跟踪技术来解决无状态问题

   2）Http请求包含两个部分:请求和响应
      ——请求:
            请求包含三个部分:1.请求行;2.请求头（请求消息头）;3.请求主体;
            ①请求行包含:1.请求的方式 2.请求的url 3.请求的协议（一般都是HTTP1.1）
            ②请求的消息头中包含很多客户端需要告诉服务器的信息,比如:我的浏览器型号、版本、我能接收的内容的类型、我给你发的内容的类型、内容的长度等等
            ③请求体,三种情况:
                   1.get方式，没有请求体，但是有一个queryString
                   2.post方式，有请求体，form data
                   3.json格式，有请求体，request payload
      ——响应:
            响应也包含三部分:1.响应行 2.响应头（响应消息头） 3.响应体
            ①响应行包含三个信息:1.协议 2.响应状态码（200） 3.响应状态（ok）
            ②响应头:包含了服务器的信息;服务器发送给浏览器的信息（内容的媒体类型、编码、内容长度等）
            ③响应体:响应的实际内容（比如请求add.html页面时，响应的内容就是<html><head><body>...）

5.会话
   1）Http是无状态的:
        ——服务器无法判断这两次请求是同一个客户端发过来的，还是不同客户端发过来的
        ——无状态带来的现实问题:第一次请求是添加商品购物车，第二次请求是结账;如果两次请求服务器无法区分是同一个用户的，那么就会混乱
        解决方法:通过会话跟踪技术来解决无状态问题
   2）会话跟踪技术:
        ——客户端第一次发请求给服务器，服务器获取session，获取不到，则创建新的，然后响应给客户端
        ——下次客户端给服务器发请求时，会把sessionID带给服务器，服务器获取到了，就可以判断这一次请求和上次请求是同一个客户端，从而能够区分客户端
        ——常用的API:
           request.getSession()      ->获取当前的会话，没有的话就创建一个新的会话
           request.getSession(true)  ->效果和不带参数相同
           request.getSession(false) ->获取当前的会话，没有则返回null，不会创建新的

           session.getId()           ->获取sessionID
           session.isNew()           ->判断当前session是否是新的
           session.getMaxInactiveInterval()   ->session的非激活间隔时长 默认1800s
           session.invalidate();     ->强制性让会话立即失效
           ...
           session.setAttribute(k,v) ->向当前session保存作用域保存一个数据 v ，对应的key为k
           session.getAttribute(k)   ->从当前session保存作用域获取指定的key，对应的value值
   3）session保存作用域:
        ——session保存作用域是和具体的某一个session对应的
        ——常用的API:
          void session.setAttribute(k,v)
          Object session.getAttribute(k)
          void removeAttribute(k)

6.服务器内部转发以及客户端重定向
   1）服务器内部转发:request.getRequestDispatcher("...").forward(request,response);
                 ——一次请求响应的过程，对于客户端而言，内部经过了多少次转发，客户端是不知道的
                 ——地址栏无变化
   2）客户端重定向:response.sendRedirect("...");
                 ——两次请求响应的过程，客户端肯定知道请求url有变化
                 ——地址栏有变化

7.Thymeleaf ——视图模板技术
   1）添加thymeleaf的jar包
   2）新建一个servlet类viewBaseServlet
   3）在web.xml文件中添加配置
      ——配置前缀 view-prefix
      ——配置后缀 view-suffix
   4）使得我们的servlet继承ViewBaseServlet

   5）根据逻辑视图名称 得到 物理视图名称
      //templateName是视图名称，此处的视图名称是index
      //那么thymeleaf 会将这个逻辑视图名称 对应到 物理视图名称 上去
      //逻辑视图名称 ： index
      //物理视图名称 ： view-prefix+逻辑视图名称+view-suffix
      //所以真实的视图名称是 ：/   index   .html
      super.processTemplate("index",req,resp);
   6）部分标签：<th:if>,<th:unless>,<th:each>···




















//200:正常响应
//404:找不到资源
//405:请求方式不支持
//500服务器内部错误




