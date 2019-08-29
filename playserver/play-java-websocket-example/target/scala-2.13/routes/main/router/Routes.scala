// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Projects/meitarim/angular_test/playserver/play-java-websocket-example/conf/routes
// @DATE:Thu Aug 29 11:31:54 IDT 2019

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:5
  HomeController_2: controllers.HomeController,
  // @LINE:7
  StockSentiment_0: controllers.StockSentiment,
  // @LINE:10
  Assets_1: controllers.Assets,
  // @LINE:12
  webjars_Routes_0: webjars.Routes,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:5
    HomeController_2: controllers.HomeController,
    // @LINE:7
    StockSentiment_0: controllers.StockSentiment,
    // @LINE:10
    Assets_1: controllers.Assets,
    // @LINE:12
    webjars_Routes_0: webjars.Routes
  ) = this(errorHandler, HomeController_2, StockSentiment_0, Assets_1, webjars_Routes_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_2, StockSentiment_0, Assets_1, webjars_Routes_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index(request:Request)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ws""", """controllers.HomeController.ws"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """sentiment/""" + "$" + """symbol<[^/]+>""", """controllers.StockSentiment.get(symbol:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.at(path:String = "/public", file:String)"""),
    prefixed_webjars_Routes_0_4.router.documentation,
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:5
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      HomeController_2.index(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Seq(classOf[play.mvc.Http.Request]),
      "GET",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val controllers_HomeController_ws1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ws")))
  )
  private[this] lazy val controllers_HomeController_ws1_invoker = createInvoker(
    HomeController_2.ws,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "ws",
      Nil,
      "GET",
      this.prefix + """ws""",
      """""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_StockSentiment_get2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("sentiment/"), DynamicPart("symbol", """[^/]+""",true)))
  )
  private[this] lazy val controllers_StockSentiment_get2_invoker = createInvoker(
    StockSentiment_0.get(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.StockSentiment",
      "get",
      Seq(classOf[String]),
      "GET",
      this.prefix + """sentiment/""" + "$" + """symbol<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_Assets_at3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_at3_invoker = createInvoker(
    Assets_1.at(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "at",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )

  // @LINE:12
  private[this] val prefixed_webjars_Routes_0_4 = Include(webjars_Routes_0.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "webjars"))


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:5
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(
          req => HomeController_2.index(req))
      }
  
    // @LINE:6
    case controllers_HomeController_ws1_route(params@_) =>
      call { 
        controllers_HomeController_ws1_invoker.call(HomeController_2.ws)
      }
  
    // @LINE:7
    case controllers_StockSentiment_get2_route(params@_) =>
      call(params.fromPath[String]("symbol", None)) { (symbol) =>
        controllers_StockSentiment_get2_invoker.call(StockSentiment_0.get(symbol))
      }
  
    // @LINE:10
    case controllers_Assets_at3_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at3_invoker.call(Assets_1.at(path, file))
      }
  
    // @LINE:12
    case prefixed_webjars_Routes_0_4(handler) => handler
  }
}
