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

package repository

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

import java.util.UUID

import com.datastax.driver.core.PagingState
import domain.Notification
import repository.support.Page

import scala.concurrent.Future

/**
 * A repository for [[Notification]]
 *
 * @author jaharzli
 */
trait NotificationRepository {

  /**
   *  Insert a new notification
   *
   * @param notification entity to insert
   * @return the inserted entity
   */
  def insert(notification: Notification): Future[Notification]

  /**
   * Update notification property seen to true
   *
   * @param id id of the notification
   */
  def updateToSeen(id: UUID): Future[Unit]

  /**
   *  Find notifications by user id
   *
   * @param userId id of the user
   * @param pagingState state of the pagination this is blank for the first page
   * @return the found notifications and the next paging state
   */
  def findByUserId(userId: UUID, pagingState: Option[PagingState]): Future[Page[List[Notification]]]
}
