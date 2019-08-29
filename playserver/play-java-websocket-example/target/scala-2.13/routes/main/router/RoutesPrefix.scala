// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Projects/meitarim/angular_test/playserver/play-java-websocket-example/conf/routes
// @DATE:Thu Aug 29 11:31:54 IDT 2019


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
