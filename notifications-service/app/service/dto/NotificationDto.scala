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

package service.dto

import java.util.UUID

import org.joda.time.DateTime

/**
  * A representation of user notification
  *
  * @author jaharzli
  */
case class UserNotificationDto(id: UUID,
                        content: String,
                        seen: Boolean,
                        createdAt: DateTime)

/**
  * A representation of notification to add
  *
  * @author jaharzli
  */
case class NotificationToAddDto(content: String, userId: UUID)
