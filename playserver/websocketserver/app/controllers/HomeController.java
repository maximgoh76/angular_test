package controllers;

import com.google.inject.Inject;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import play.api.libs.streams.ActorFlow;
import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	ActorSystem actorSystem = null;
	Materializer materializer = null;
	@Inject
	public HomeController (ActorSystem actorSystem) {
		this.actorSystem = actorSystem;
		materializer = ActorMaterializer.create(actorSystem);
	}
	
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }
//    public WebSocket ws() {
//    	
//		
//        //return WebSocket.Text.accept(request -> ActorFlow.actorRef((out) -> WebSocketActor.props(out), actorSystem, materializer));
//    }
}
