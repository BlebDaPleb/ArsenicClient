/*
 * This file is part of the BleachHack distribution (https://github.com/BleachDev/BleachHack/).
 * Copyright (c) 2021 Bleach and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package blebdapleb.arsenic.arsenic.event.events;

import net.minecraft.util.Hand;
import blebdapleb.arsenic.arsenic.event.Event;

public class EventSwingHand extends Event {

	private Hand hand;

	public EventSwingHand(Hand hand) {
		this.setHand(hand);
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}
}
