/*
 * Copyright (C) 2018  Joumen Ali HARZLI
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package server

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives.{handleWebSocketMessages, parameters, path, _}
import akka.http.scaladsl.server.Route
import handler.WebSocketHandler

/**
 * Web Server Routes
 *
 * @author jaharzli
 */
object Routes {

  def routes(implicit system: ActorSystem, eventsConsumer: ActorRef): Route =
    path("ws") {
      parameters('id.as[String]) { id =>
        {
          handleWebSocketMessages(WebSocketHandler.handle(id))
        }
      }

    }

}
