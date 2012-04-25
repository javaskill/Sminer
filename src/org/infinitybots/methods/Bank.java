package org.infinitybots.methods;

import org.powerbot.game.api.methods.*;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.*;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.node.SceneObjectDefinition;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.infinitybots.wrappers.*;

import java.awt.*;

/**
 * Bank related operations.
 */
public class Bank {
	
	public static final int[] BANKERS = new int[]{44, 45, 494, 495, 496, 497,
			498, 499, 553, 909, 958, 1036, 2271, 2354, 2355, 2718, 2759, 3198,
			3293, 3416, 3418, 3824, 4456, 4457, 4458, 4459, 5488, 5901, 5912,
			6362, 6532, 6533, 6534, 6535, 7605, 8948, 9710, 14367};
	public static final int[] BANK_BOOTHS = new int[]{782, 2213, 2995, 5276,
			6084, 10517, 11402, 11758, 12759, 14367, 19230, 20325, 24914, 11338,
			25808, 26972, 29085, 52589, 34752, 35647, 36786, 2012, 2015, 2019,
			42217, 42377, 42378};
	public static final int[] BANK_CHESTS = new int[]{2693, 4483, 8981, 12308, 21301, 20607,
			21301, 27663, 42192};
	public static final int[] DEPOSIT_BOXES = {2045, 9398, 20228, 24995, 25937,
			26969, 32924, 32930, 32931, 34755, 36788, 39830, 45079};
	public static final int[] DO_NOT_DEPOSIT = new int[]{1265, 1267, 1269,
			1273, 1271, 1275, 1351, 590, 303};


	public static final int INTERFACE_BANK = 762;
	public static final int INTERFACE_BANK_BUTTON_CLOSE = 45;
	public static final int INTERFACE_BANK_BUTTON_DEPOSIT_BEAST_INVENTORY = 40;
	public static final int INTERFACE_BANK_BUTTON_DEPOSIT_CARRIED_ITEMS = 34;
	public static final int INTERFACE_BANK_BUTTON_DEPOSIT_WORN_ITEMS = 38;
	public static final int INTERFACE_BANK_BUTTON_HELP = 46;
	public static final int INTERFACE_BANK_BUTTON_INSERT = 15;
	public static final int INTERFACE_BANK_BUTTON_ITEM = 19;
	public static final int INTERFACE_BANK_BUTTON_NOTE = 19;
	public static final int INTERFACE_BANK_BUTTON_SEARCH = 17;
	public static final int INTERFACE_BANK_BUTTON_SWAP = 15;
	public static final int INTERFACE_BANK_BUTTON_OPEN_EQUIP = 120;
	public static final int INTERFACE_BANK_INVENTORY = 95;
	public static final int INTERFACE_BANK_ITEM_FREE_COUNT = 29;
	public static final int INTERFACE_BANK_ITEM_FREE_MAX = 30;
	public static final int INTERFACE_BANK_ITEM_MEMBERS_COUNT = 31;
	public static final int INTERFACE_BANK_ITEM_MEMBERS_MAX = 32;
	public static final int INTERFACE_BANK_SCROLLBAR = 116;
	public static final int INTERFACE_BANK_SEARCH = 752;
	public static final int INTERFACE_BANK_SEARCH_INPUT = 5;

	public static final int INTERFACE_EQUIPMENT = 667;
	public static final int INTERFACE_EQUIPMENT_COMPONENT = 7;

	public static final int INTERFACE_COLLECTION_BOX = 109;
	public static final int INTERFACE_COLLECTION_BOX_CLOSE = 14;

	public static final int[] INTERFACE_BANK_TAB = {65, 63, 61, 59, 57, 55, 53, 51, 49 };
	public static final int[] INTERFACE_BANK_TAB_FIRST_ITEM = {78, 79, 80, 81, 82, 83, 84, 85, 86};

	public static final int INTERFACE_DEPOSIT_BOX = 11;
	public static final int INTERFACE_DEPOSIT_BOX_BUTTON_CLOSE = 15;
	public static final int INTERFACE_DEPOSIT_BUTTON_DEPOSIT_BEAST_INVENTORY = 25;
	public static final int INTERFACE_DEPOSIT_BUTTON_DEPOSIT_CARRIED_ITEMS = 19;
	public static final int INTERFACE_DEPOSIT_BUTTON_DEPOSIT_WORN_ITEMS = 23;


	private static void sleep(final int min, final int max){
		try {
			Thread.sleep(Random.nextInt(min, max));
		} catch(Exception e){}
	}
	/**
	 * Closes the bank interface. Supports deposit boxes.
	 *
	 * @return <tt>true</tt> if the bank interface is no longer open.
	 */
	public static boolean close() {
		if (isOpen()) {
			Widgets.get(INTERFACE_BANK, INTERFACE_BANK_BUTTON_CLOSE).click(true);
			sleep(500, 600);
			return !isOpen();
		}
		if (isDepositOpen()) {
			Widgets.get(INTERFACE_DEPOSIT_BOX, INTERFACE_DEPOSIT_BOX_BUTTON_CLOSE).click(true);
			sleep(500, 600);
			return !isDepositOpen();
		}
		return !isOpen() && !isDepositOpen();
	}

	/**
	 * If bank is open, deposits specified amount of an item into the bank.
	 * Supports deposit boxes.
	 *
	 * @param itemID The ID of the item.
	 * @param number The amount to deposit. 0 deposits All. 1,5,10 deposit
	 *               corresponding amount while other numbers deposit X.
	 * @return <tt>true</tt> if successful; otherwise <tt>false</tt>.
	 */
	public static boolean deposit(final int itemID, final int number) {
		if (isOpen() || isDepositOpen()) {
			if (number < 0) {
				throw new IllegalArgumentException("number < 0 (" + number + ")");
			}
			RSWidgetChild item = null;
			int itemCount = 0;
			final int invCount = isOpen() ? Inventory.getCount(true) : getBoxCount();
			if (!isOpen()) {
				boolean match = false;
				for (int i = 0; i < 28; i++) {
					final RSWidgetChild comp = Interfaces.getWidget(11).getChild(17).getChild(i);
					if (comp.getChildId() == itemID) {
						itemCount += comp.getChildStackSize();
						if (!match) {
							item = comp;
							match = true;
						}
					}
					if (itemCount > 1) {
						break;
					}
				}
			} else {
				item = new RSItem(Inventory.getItem(itemID)).getWidgetChild();
				itemCount = Inventory.getCount(true, itemID);
			}
			if (item == null) {
				return true;
			}
			switch (number) {
				case 0:
					item.interact(itemCount > 1 ? "Deposit-All" : "Deposit");
					break;
				case 1:
					item.interact("Deposit");
					break;
				case 5:
					item.interact("Deposit-" + number);
					break;
				default:
					if (!item.interact("Deposit-" + number)) {
						if (item.interact("Deposit-X")) {
							sleep(1000, 1300);
							Keyboard.sendText(String.valueOf(number), true);
						}
					}
					break;
			}
			sleep(250,350);
			final int cInvCount = isOpen() ? Inventory.getCount(true) : getBoxCount();
			return cInvCount < invCount || cInvCount == 0;
		}
		return false;
	}
	/**
	 * Deposits all items in methods.inventory. Supports deposit boxes.
	 *
	 * @return <tt>true</tt> on success.
	 */
	public static boolean depositAll() {
		if(Inventory.getCount() == 0)
			return true;
		if (isOpen()) {
			return Widgets.get(INTERFACE_BANK, INTERFACE_BANK_BUTTON_DEPOSIT_CARRIED_ITEMS).click(true);
		}
		return isDepositOpen() && Widgets.get(INTERFACE_DEPOSIT_BOX, INTERFACE_DEPOSIT_BUTTON_DEPOSIT_CARRIED_ITEMS).click(true);
	}

	/**
	 * Deposits all items in inventory except for the given IDs. Supports
	 * deposit boxes.
	 *
	 * @param items The items not to deposit.
	 * @return true on success.
	 */
	public static boolean depositAllExcept(final int... items) {
		if (isOpen() || isDepositOpen()) {
			boolean deposit = true;
			int invCount = isOpen() ? Inventory.getCount(true) : getBoxCount();
			outer:
			for (int i = 0; i < 28; i++) {
				final RSWidgetChild item = isOpen() ? new RSItem(Inventory.getItemAt(i)).getWidgetChild() : Interfaces.getWidget(11).getChild(17).getChild(i);
				if (item != null && item.getChildId() != -1) {
					for (final int id : items) {
						if (item.getChildId() == id) {
							continue outer;
						}
					}
					for (int tries = 0; tries < 5; tries++) {
						deposit(item.getChildId(), 0);
						sleep(600, 900);
						final int cInvCount = isOpen() ? Inventory.getCount(true) : getBoxCount();
						if (cInvCount < invCount) {
							invCount = cInvCount;
							continue outer;
						}
					}
					deposit = false;
				}
			}
			return deposit;
		}
		return false;
	}
	/**
	 * 
	 * @return True if an undefined bank is in the loaded region
	 */
	public static boolean isNear(){
		Object[] banks = {SceneEntities.getNearest(Bank.BANK_CHESTS), SceneEntities.getNearest(Bank.BANK_BOOTHS), NPCs.getNearest(Bank.BANKERS)};
		return banks[0] != null || banks[1] != null || banks[2] != null;
	}
	/**
	 * Deposit everything your player has equipped. Supports deposit boxes.
	 *
	 * @return <tt>true</tt> on success.
	 * @since 6 March 2009.
	 */
	public static boolean depositAllEquipped() {
		if (isOpen()) {
			return Widgets.get(INTERFACE_BANK, INTERFACE_BANK_BUTTON_DEPOSIT_WORN_ITEMS).click(true);
		}
		return isDepositOpen() && Widgets.get(INTERFACE_DEPOSIT_BOX, INTERFACE_DEPOSIT_BUTTON_DEPOSIT_WORN_ITEMS).click(true);
	}

	/**
	 * Deposits everything your familiar is carrying. Supports deposit boxes.
	 *
	 * @return <tt>true</tt> on success
	 * @since 6 March 2009.
	 */
	public static boolean depositAllFamiliar() {
		if (isOpen()) {
			return Widgets.get(INTERFACE_BANK, INTERFACE_BANK_BUTTON_DEPOSIT_BEAST_INVENTORY).click(true);
		}
		return isDepositOpen() && Widgets.get(INTERFACE_DEPOSIT_BOX, INTERFACE_DEPOSIT_BUTTON_DEPOSIT_BEAST_INVENTORY).click(true);
	}

	/**
	 * Returns the sum of the count of the given items in the bank.
	 *
	 * @param items The array of items.
	 * @return The sum of the stacks of the items.
	 */
	public static int getCount(final int... items) {
		int itemCount = 0;
		final RSItem[] inventoryArray = getItems();
		for (final RSItem item : inventoryArray) {
			for (final int id : items) {
				if (item.getID() == id) {
					itemCount += item.getStackSize();
				}
			}
		}
		return itemCount;
	}

	/**
	 * Get current tab open in the bank.
	 *
	 * @return int of tab (0-8), or -1 if none are selected (bank is not open).
	 */
	public static int getCurrentTab() {
		//for (int i = 0; i < INTERFACE_BANK_TAB.length; i++) {
		//	if (methods.interfaces.get(INTERFACE_BANK).getComponent(INTERFACE_BANK_TAB[i] - 1).getBackgroundColor() == 1419) {
		//		return i;
		//	}
		//}
		//return -1;
		return ((Settings.get(1248) >>> 24) - 136) / 8;
	}

	/**
	 * Gets the bank interface.
	 *
	 * @return The bank <code>RSInterface</code>.
	 */
	public static RSWidget getInterface() {
		return Interfaces.getWidget(INTERFACE_BANK);
	}

	/**
	 * Gets the deposit box interface.
	 *
	 * @return The deposit box <code>RSInterface</code>.
	 */
	public static Widget getBoxInterface() {
		return Widgets.get(INTERFACE_BANK);
	}

	/**
	 * Gets the <code>RSComponent</code> of the given item at the specified index.
	 *
	 * @param index The index of the item.
	 * @return <code>RSComponent</code> if item is found at index; otherwise null.
	 */
	public static RSItem getItemAt(final int index) {
		final RSItem[] items = getItems();
		if (items != null) {
			for (final RSItem item : items) {
				if (item.getWidgetChild().getIndex() == index) {
					return item;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the first item with the provided ID/Name in the bank.
	 *
	 * @param item ID/Name of the item to get.
	 * @return The component of the item; otherwise null.
	 */
	public static RSItem getItem(final Object item) {
		final RSItem[] Items = getItems();
		if (Items != null) {
			for (final RSItem Item : Items) {
				if (item instanceof Integer) {
					if (Item.getID() == (Integer) item) {
						return Item;
					}
				}
				if (item instanceof String) {
					if (Item.getName().equalsIgnoreCase(item.toString())) {
						return Item;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets the point on the screen for a given item. Numbered left to right then top to bottom.
	 *
	 * @param slot The index of the item.
	 * @return The point of the item or new Point(-1, -1) if null.
	 */
	public static Point getItemPoint(final int slot) {
		if (slot < 0) {
			throw new IllegalArgumentException("slot < 0 " + slot);
		}
		final RSItem item = getItemAt(slot);
		if (item != null) {
			return item.getWidgetChild().getAbsoluteLocation();
		}
		return new Point(-1, -1);
	}

	/**
	 * Gets all the items in the bank's inventory.
	 *
	 * @return an <code>RSItem</code> array of the bank's inventory interface.
	 */
	public static RSItem[] getItems() {
		if (getInterface() == null || getInterface().getChild(INTERFACE_BANK_INVENTORY) == null) {
			return new RSItem[0];
		}
		final WidgetChild[] components = Widgets.get(Bank.INTERFACE_BANK).getChild(INTERFACE_BANK_INVENTORY).getChildren();
		final RSItem[] items = new RSItem[components.length];
		for (int i = 0; i < items.length; ++i) {
			items[i] = new RSItem(new Item(components[i]));
		}
		return items;
	}

	/**
	 * Checks whether or not the bank is open.
	 *
	 * @return <tt>true</tt> if the bank interface is open; otherwise <tt>false</tt>.
	 */
	public static boolean isOpen() {
		return getInterface().validate();
	}

	/**
	 * Checks whether or not the deposit box is open.
	 *
	 * @return <tt>true</tt> if the deposit box interface is open; otherwise <tt>false</tt>.
	 */
	public static boolean isDepositOpen() {
		return Widgets.get(INTERFACE_DEPOSIT_BOX).validate();
	}

	/**
	 * Opens one of the supported banker NPCs, booths, or chests nearby. If they
	 * are not nearby, and they are not null, it will automatically walk to the
	 * closest one.
	 *
	 * @return <tt>true</tt> if the bank was opened; otherwise <tt>false</tt>.
	 */
	public static boolean open() {
		if (isOpen()) {
			return true;
		}
		try {
			if (Menu.isOpen()) {
				int x = Mouse.getLocation().x + Random.nextInt(-225,225);
				int y = Mouse.getLocation().x + Random.nextInt(-225,225);
				Mouse.move(x,y);
				sleep(40, 80);
			}
			Object[] banks = {SceneEntities.getNearest(Bank.BANK_CHESTS), SceneEntities.getNearest(Bank.BANK_BOOTHS), NPCs.getNearest(Bank.BANKERS)};
			int chests = Integer.MAX_VALUE;
			int booths =  Integer.MAX_VALUE;
			int bankers = Integer.MAX_VALUE;
			try {
				chests = (int) Calculations.distanceTo((SceneObject)banks[0]);
				booths = (int) Calculations.distanceTo((SceneObject) banks[1]);
				bankers = (int)Calculations.distanceTo((NPC) banks[2]);
			} catch(Exception e){}
			int[] dis = { chests, booths, bankers};
			String[][] actions = {{"Open", "Use", "Bank"}, {"Bank"}, {"Bank"}};
			SceneObject object = (SceneObject) banks[1];
			NPC npc = (NPC) banks[2];
			String[] action = npc != null ? actions[2] : object != null ? actions[1] : null;
			/** find closest */
			if (object != null && npc != null) {
				if (dis[1] < dis[2]) {
					object = (SceneObject) banks[1];
					action = actions[1];
					npc = null;
				} else {
					npc = (NPC) banks[2];
					action = actions[2];
					object = null;
				}
			}
			if (object == null && npc == null) {
				object = (SceneObject) banks[0];
				action = actions[0];
			}
			Tile tile = object == null ? npc == null ? null : npc.getLocation() : object.getLocation();
			String finalAction = null;
			boolean didAction = false;
			if (object != null && Calculations.distanceTo(object) < 5) {
				Mouse.move(object.getCentralPoint());
				
				SceneObjectDefinition def = object.getDefinition();
				if (def != null) {
					outer:
					for (String s : action) {
						for (String i : def.getActions()) { // find correct action; instead of trying all.
							if (i != null && i.contains(s)) {
								finalAction = s;
								break outer;
							}
						}
					}
					didAction = finalAction != null && object.interact(finalAction + " " + object.getDefinition().getName());
				} else {
					for (int i = 0; i < action.length && !didAction; i++) {
						didAction = object.interact(action[i]);
					}
				}
			} else if (npc != null && Calculations.distanceTo(npc.getLocation()) < 5) {
				Mouse.move(npc.getCentralPoint());
				outer:
				for (String s : action) {
					for (String i : npc.getActions()) {
						if (i != null && i.contains(s)) { // finding correct action
							finalAction = s;
							break outer;
						}
					}
				}
				didAction = npc.interact(finalAction);
			} else if (tile != null) {
				Walking.walk(tile);
			}
			if (didAction) {
				int count = 0;
				while (!isOpen() && ++count < 10) {
					sleep(100, 200);
					if (Players.getLocal().isMoving()) {
						count = 0;
					}
				}
			}
			return isOpen();
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens one of the supported deposit boxes nearby. If they are not nearby, and they are not null,
	 * it will automatically walk to the closest one.
	 *
	 * @return <tt>true</tt> if the deposit box was opened; otherwise
	 *         <tt>false</tt>.
	 */
	public static boolean openDepositBox() {
		if (isDepositOpen()) {
			return false;
		}
		try {
			if (Menu.isOpen()) {
				int x = Mouse.getLocation().x + Random.nextInt(-50,50);
				int y = Mouse.getLocation().x + Random.nextInt(-50,50);
				Mouse.move(x,y);
				sleep(20, 30);
			}
			final SceneObject depositBox = SceneEntities.getNearest(DEPOSIT_BOXES);
			if (depositBox == null) {
				return false;
			}
			if (Calculations.distanceTo(depositBox) < 5 && Calculations.distanceTo(depositBox.getLocation()) < 15) {
				if (depositBox.interact("Deposit")) {
					int count = 0;
					while (!isDepositOpen() && ++count < 10) {
						sleep(200, 400);
						if (Players.getLocal().isMoving()) {
							count = 0;
						}
					}
				} else {
					Camera.turnTo(depositBox, 20);
				}
			} else {
				if (!Walking.walk(depositBox.getLocation())) {
					Walking.walk(depositBox.getLocation());
				}
			}
			return isDepositOpen();
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens the bank tab.
	 *
	 * @param tabNumber The tab number - e.g. view all is 1.
	 * @return <tt>true</tt> on success.
	 */
	public static boolean openTab(final int tabNumber) {
		return isOpen() && Widgets.get(INTERFACE_BANK, INTERFACE_BANK_TAB[tabNumber - 1]).click(true);
	}

	/**
	 * @return <tt>true</tt> if currently searching the bank.
	 */
	public static boolean isSearchOpen() {
		// Setting 1248 is -2147483648 when search is enabled and -2013265920
		return Settings.get(1248) == -2147483648;
	}

	/**
	 * Searches for an item in the bank. Returns true if succeeded (does not
	 * necessarily mean it was found).
	 *
	 * @param itemName The item name to find.
	 * @return <tt>true</tt> on success.
	 */
	public static boolean searchItem(final String itemName) {
		if (!isOpen()) {
			return false;
		}
		Widgets.get(INTERFACE_BANK, INTERFACE_BANK_BUTTON_SEARCH).interact("Search");
		sleep(1000, 1500);
		if (!isSearchOpen()) {
			sleep(450,550);
		}
		if (isOpen() && isSearchOpen()) {
			Keyboard.sendText(itemName, false);
			sleep(300, 700);
			return true;
		}
		return false;
	}
	private static boolean scrollTo(final RSWidgetChild component, final RSWidgetChild scrollBar) {
		if (component == null || scrollBar == null || !component.validate()) {
			return false;
		}
		if (scrollBar.getChildren().length != 6) {
			return true;
		}

		RSWidgetChild scrollableArea = component;
		while (scrollableArea.getScrollableContentHeight() == 0
				&& scrollableArea.getParentId() != -1) {
			scrollableArea = getComponent(scrollableArea.getParentId());
		}
		if (scrollableArea.getScrollableContentHeight() == 0) {
			return false;
		}
		final int areaY = scrollableArea.getAbsoluteY();
		final int areaHeight = scrollableArea.getHeight();

		if (component.getAbsoluteY() >= areaY
				&& component.getAbsoluteY() <= areaY + areaHeight
				- component.getHeight()) {
			return true;
		}
		final RSWidgetChild scrollBarArea = scrollBar.getChildren()[0];
		final int contentHeight = scrollableArea.getScrollableContentHeight();

		int pos = (int) ((float) scrollBarArea.getHeight() / contentHeight * (component.getRelativeY() + Random.nextInt(-areaHeight / 2, areaHeight / 2 - component.getHeight())));
		if (pos < 0)
		{
			pos = 0;
		} else if (pos >= scrollBarArea.getHeight()) {
			pos = scrollBarArea.getHeight() - 1;
		}
		Mouse.click(scrollBarArea.getAbsoluteX() + Random.nextInt(0, scrollBarArea.getWidth()), scrollBarArea.getAbsoluteY() + pos, true);
		sleep(200, 400);

		while (component.getAbsoluteY() < areaY || component.getAbsoluteY() > areaY + areaHeight - component.getHeight()) {
			final boolean scrollUp = component.getAbsoluteY() < areaY;
			scrollBar.getChildren()[(scrollUp ? 4 : 5)].interact("");
			sleep(100, 200);
		}
		return component.getAbsoluteY() >= areaY && component.getAbsoluteY() <= areaY + areaHeight - component.getHeight();
	}
	/**
	 * Tries to withdraw an item.
	 * 0 is All. -1 is All but one, 1, 5, 10 use Withdraw 1, 5, 10 while other numbers Withdraw X.
	 *
	 * @param itemID The ID of the item.
	 * @param count  The number to withdraw.
	 * @return <tt>true</tt> on success.
	 */
	public static boolean withdraw(final int itemID, final int count) {
		if (!isOpen()) {
			return false;
		}
		if (count < -1) {
			throw new IllegalArgumentException("count (" + count + ") < -1");
		}
		final RSItem rsi = getItem(itemID);
		if (rsi == null || rsi.getID() == -1) {
			return false;
		}
		final RSWidgetChild item = rsi.getWidgetChild();
		if (item == null) {
			return false;
		}
		int t = 0;
		while (item.getRelativeX() == 0 && getCurrentTab() != 0 && t < 5) {
			if (Widgets.get(Bank.INTERFACE_BANK, Bank.INTERFACE_BANK_TAB[0]).click(true)) {
				sleep(800, 1300);
			}
			t++;
		}
		if (!scrollTo(item, getComponent((Bank.INTERFACE_BANK << 16) + Bank.INTERFACE_BANK_SCROLLBAR))) {
			return false;
		}
		final int invCount = Inventory.getCount(true);
		item.click(count == 1);
		final String defaultAction = "Withdraw-" + count;
		String action = null;
		switch (count) {
			case 0:
				action = "Withdraw-All";
				break;
			case -1:
				action = "Withdraw-All but one";
				break;
			case 1:
				break;
			case 5:
				action = defaultAction;
				break;
			case 10:
				action = defaultAction;
				break;
			default:
				int i = -1;
				try {
					i = Integer.parseInt(item.getActions()[3].toLowerCase().trim().replaceAll("\\D", ""));
				} catch (final Exception e) {
					e.printStackTrace();
				}
				if (i == count) {
					action = defaultAction;
				} else if (item.interact("Withdraw-X")) {
					sleep(1000, 1300);
					Keyboard.sendText(String.valueOf(count), true);
				}
		}
		if (action != null && item.interact(action)) {
			sleep(1000, 1300);
		}
		final int newInvCount = Inventory.getCount(true);
		return newInvCount > invCount;
	}
	public static RSWidgetChild getComponent(final int id) {
		final int x = id >> 16;
		final int y = id & 0xFFFF;
		return new RSWidgetChild(Widgets.get(x).getChild(y));
	}
	/**
	 * Tries to withdraw an item.
	 * 0 is All. -1 is All but one, 1, 5, 10 use Withdraw 1, 5, 10 while other numbers Withdraw X.
	 *
	 * @param itemName The Name of the item.
	 * @param count    The number to withdraw.
	 * @return <tt>true</tt> on success.
	 */
	public static boolean withdraw(final String itemName, final int count) {
		return withdraw(getItem(itemName).getID(), count);
	}

	/**
	 * Gets the count of all the items in the inventory with the any of the
	 * specified IDs while deposit box is open.
	 *
	 * @param ids the item IDs to include
	 * @return The count.
	 */
	public static int getBoxCount(final int... ids) {
		if (!isDepositOpen()) {
			return -1;
		}
		int count = 0;
		for (int i = 0; i < 28; ++i) {
			for (final int id : ids) {
				if (Widgets.get(11).getChild(17).validate() && Widgets.get(11).getChild(17).getChild(i).getChildId() == id) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Gets the count of all items in your inventory ignoring stack sizes while
	 * deposit box is open.
	 *
	 * @return The count.
	 */
	public static int getBoxCount() {
		if (!isDepositOpen()) {
			return -1;
		}
		int count = 0;
		for (int i = 0; i < 28; i++) {
			if (Widgets.get(11).getChild(17).validate() && Widgets.get(11).getChild(17).getChild(i).getChildId() != -1) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Gets the equipment items from the bank interface.
	 *
	 * @return All equipment items that are being worn.
	 */
	public static RSItem[] getEquipmentItems() {
		if (Widgets.get(INTERFACE_EQUIPMENT).getChild(INTERFACE_EQUIPMENT_COMPONENT).validate()) {
			return new RSItem[0];
		}
		final WidgetChild[] components = Widgets.get(INTERFACE_EQUIPMENT).getChild(INTERFACE_EQUIPMENT_COMPONENT).getChildren();
		final RSItem[] items = new RSItem[components.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = new RSItem(new Item(components[i]));
		}
		return items;
	}

	/**
	 * Gets a equipment item from the bank interface.
	 *
	 * @param id ID of the item.
	 * @return RSItem
	 */
	public static RSItem getEquipmentItem(final int id) {
		final RSItem[] items = getEquipmentItems();
		if (items != null) {
			for (final RSItem item : items) {
				if (item.getID() == id) {
					return item;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the ID of a equipment item based on name.
	 *
	 * @param name Name of the item.
	 * @return -1 if item is not found.
	 */
	public static int getEquipmentItemID(final String name) {
		final RSItem[] items = getEquipmentItems();
		if (items != null) {
			for (final RSItem item : items) {
				if (item.getName().contains(name)) {
					return item.getID();
				}
			}
		}
		return -1;
	}

	/**
	 * Opens the equipment interface.
	 *
	 * @return <tt>true</tt> if opened.
	 */
	public static boolean openEquipment() {
		return getInterface().getChild(INTERFACE_BANK_BUTTON_OPEN_EQUIP).validate() && getInterface().getChild(INTERFACE_BANK_BUTTON_OPEN_EQUIP).click(true);
	}

	/**
	 * Gets the item ID of a item side the bank.
	 *
	 * @param name Name of the item.
	 * @return -1 if item is not found.
	 */
	public static int getItemID(final String name) {
		final RSItem[] items = getItems();
		if (items != null) {
			for (final RSItem item : items) {
				if (item.getName().toLowerCase().equals(name.toLowerCase())) {
					return item.getID();
				}
			}
			for (final RSItem item : items) {
				if (item.getName().toLowerCase().contains(name.toLowerCase())) {
					return item.getID();
				}
			}
		}
		return -1;
	}
}