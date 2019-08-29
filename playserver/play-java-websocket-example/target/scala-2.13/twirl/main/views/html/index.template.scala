
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import play.data._
import play.core.j.PlayFormsMagicForJava._

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[play.mvc.Http.Request,org.webjars.play.WebJarsUtil,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(request: play.mvc.Http.Request, webJarsUtil: org.webjars.play.WebJarsUtil):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""<!DOCTYPE html>

<html>
<head>
    <title>Reactive Stock News Dashboard</title>

    <link rel="shortcut icon" type="image/png" href=""""),_display_(/*8.55*/routes/*8.61*/.Assets.at("images/favicon.png")),format.raw/*8.93*/("""">


    """),_display_(/*11.6*/webJarsUtil/*11.17*/.locate("bootstrap.min.css").css()),format.raw/*11.51*/("""
    """),format.raw/*12.5*/("""<link rel="stylesheet" media="screen" href=""""),_display_(/*12.50*/routes/*12.56*/.Assets.at("stylesheets/main.min.css")),format.raw/*12.94*/("""">

    """),_display_(/*14.6*/webJarsUtil/*14.17*/.locate("jquery.min.js").script()),format.raw/*14.50*/("""
    """),_display_(/*15.6*/webJarsUtil/*15.17*/.locate("jquery.flot.js").script()),format.raw/*15.51*/("""
    """),format.raw/*16.5*/("""<script type='text/javascript' src='"""),_display_(/*16.42*/routes/*16.48*/.Assets.at("javascripts/index.js")),format.raw/*16.82*/("""'></script>
</head>
<body data-ws-url=""""),_display_(/*18.21*/routes/*18.27*/.HomeController.ws.webSocketURL(request)),format.raw/*18.67*/("""">
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container">
                <a class="brand" href="#">Reactive Stocks</a>
                <form id="addsymbolform" class="navbar-form pull-right">
                    <input id="addsymboltext" type="text" class="span2" placeholder="SYMBOL">
                    <button type="submit" class="btn">Add Stock</button>
                </form>
            </div>
        </div>
    </div>

    <div id="stocks" class="container">

    </div>
</body>
</html>
"""))
      }
    }
  }

  def render(request:play.mvc.Http.Request,webJarsUtil:org.webjars.play.WebJarsUtil): play.twirl.api.HtmlFormat.Appendable = apply(request,webJarsUtil)

  def f:((play.mvc.Http.Request,org.webjars.play.WebJarsUtil) => play.twirl.api.HtmlFormat.Appendable) = (request,webJarsUtil) => apply(request,webJarsUtil)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2019-08-29T11:31:55.734
                  SOURCE: C:/Projects/meitarim/angular_test/playserver/play-java-websocket-example/app/views/index.scala.html
                  HASH: 7690a520ac14adf483cbf2f488a18db44a503545
                  MATRIX: 992->1|1161->77|1322->212|1336->218|1388->250|1424->260|1444->271|1499->305|1531->310|1603->355|1618->361|1677->399|1712->408|1732->419|1786->452|1818->458|1838->469|1893->503|1925->508|1989->545|2004->551|2059->585|2126->625|2141->631|2202->671
                  LINES: 28->1|33->2|39->8|39->8|39->8|42->11|42->11|42->11|43->12|43->12|43->12|43->12|45->14|45->14|45->14|46->15|46->15|46->15|47->16|47->16|47->16|47->16|49->18|49->18|49->18
                  -- GENERATED --
              */
          