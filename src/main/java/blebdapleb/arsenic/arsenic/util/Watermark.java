/*
 * This file is part of the BleachHack distribution (https://github.com/BleachDev/BleachHack/).
 * Copyright (c) 2021 Bleach and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package blebdapleb.arsenic.arsenic.util;


import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class Watermark {

	private static String text = "Arsenic";
	private static int color = 0xb54ecc;

	public static MutableText getText() {
		MutableText t = Text.literal(text).styled(s -> s.withColor(color));
		return t;
	}
}
