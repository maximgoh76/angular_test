import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs/Rx";
import { WebsocketService } from "../websocket/websocket.service"


const CHAT_URL = "ws://127.0.0.1:8080/mediaChat";

export interface Message {
  author: string;
  message: string;
}

@Injectable()
export class ChatService {
  public messages: Subject<Message>;

  constructor(wsService: WebsocketService) {
    this.messages = <Subject<Message>>wsService.connect(CHAT_URL).map(
      (response: MessageEvent): Message => {
        console.log(response.data)
        //let data = JSON.parse(response.data);
        return {
          author: "a",
          message: response.data
        };
      }
    );
  }
}