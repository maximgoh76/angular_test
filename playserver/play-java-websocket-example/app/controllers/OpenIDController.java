package controllers;


import java.util.*;
import java.util.concurrent.CompletionStage;

import play.data.*;
import play.libs.openid.*;
import play.mvc.*;
import play.twirl.api.Html;

import javax.inject.Inject;

public class OpenIDController extends Controller {

  @Inject OpenIdClient openIdClient;

  @Inject FormFactory formFactory;

  public Result login() {
    return ok(views.html.login.render(""));
  }

  public CompletionStage<Result> loginPost(Http.Request request) {

    // Form data
    DynamicForm requestData = formFactory.form().bindFromRequest(request);
    String openID = requestData.get("openID");

    CompletionStage<String> redirectUrlPromise =
        openIdClient.redirectURL(
            openID, routes.OpenIDController.openIDCallback().absoluteURL(request));

    return redirectUrlPromise
        .thenApply(Controller::redirect)
        .exceptionally(throwable -> badRequest(views.html.login.render(throwable.getMessage())));
  }

  public CompletionStage<Result> openIDCallback(Http.Request request) {

    CompletionStage<UserInfo> userInfoPromise = openIdClient.verifiedId(request);

    CompletionStage<Result> resultPromise =
        userInfoPromise
            .thenApply(userInfo -> ok(userInfo.id() + "\n" + userInfo.attributes()))
            .exceptionally(
                throwable -> badRequest(views.html.login.render(throwable.getMessage())));

    return resultPromise;
  }

  public static class views {
    public static class html {
      public static class login {
        public static Html render(String msg) {
        	Html h = new Html("Hi");
          return h;// javaguide.ws.html.login.render(msg);
        }
      }
    }
  }
}