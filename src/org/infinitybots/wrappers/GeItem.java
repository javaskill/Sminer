package org.infinitybots.wrappers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Grand Exchange lookup convenience wrapper.
 * <br>
 * Use {@link GeItem#lookup(int)} or {@link GeItem#lookup(String)} to define a GeItem.
 * 
 * @author Vulcan
 */
public final class GeItem {

	public static final String HOST = "http://services.runescape.com";
	public static final String GET_ID = "/m=itemdb_rs/api/catalogue/detail.json?item=";
	public static final String GET_NAME = "/m=itemdb_rs/results.ws?query=";

	private int id;
	private int price;
	private int changeToday;
	private double[] changes;
	private boolean members;
	private String name;
	private String description;
	private String iconUrl;
	private String largeIconUrl;
	private String type;
	private String typeIconUrl;

	private GeItem(final int id, final String name, final int price, final boolean members,
			final int changeToday, final double[] changes, final String description, final String iconUrl,
			final String largeIconUrl, final String type, final String typeIconUrl) {
		this.id = id;
		this.price = price;
		this.changeToday = changeToday;
		this.changes = changes;
		this.members = members;
		this.name = name;
		this.description = description;
		this.iconUrl = iconUrl;
		this.largeIconUrl = largeIconUrl;
		this.type = type;
		this.typeIconUrl = typeIconUrl;
	}

	/**
	 * Gets the item's 30 day change percentage.
	 * @return The item's 30 day change percentage.
	 */
	public double getChange30Days() {
		return changes[0];
	}

	/**
	 * Gets the item's 90 day change percentage.
	 * @return The item's 90 day change percentage.
	 */
	public double getChange90Days() {
		return changes[1];
	}

	/**
	 * Gets the item's 180 day change percentage.
	 * @return The item's 180 day change percentage.
	 */
	public double getChange180Days() {
		return changes[2];
	}

	/**
	 * Gets the item's change today.
	 * @return The item's change today.
	 */
	public int getChangeToday() {
		return changeToday;
	}

	/**
	 * Gets the item's description.
	 * @return The item's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the item's icon url.
	 * @return The item's icon url.
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * Gets the item's id.
	 * @return The item's id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the item's large icon url.
	 * @return The item's large icon url.
	 */
	public String getLargeIconUrl() {
		return largeIconUrl;
	}

	/**
	 * Gets the item's name.
	 * @return The item's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the item's current price.
	 * @return The item's current price.
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Gets the item's type category.
	 * @return The item's type category.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the item's type category icon url.
	 * @return The item's type category icon url.
	 */
	public String getTypeIconUrl() {
		return typeIconUrl;
	}

	/**
	 * Checks if the item is members-only.
	 * @return <tt>true</tt> if members; otherwise <tt>false</tt>.
	 */
	public boolean isMembers() {
		return members;
	}

	/**
	 * Looks up the GeItem of a given id, returning null if failed.
	 * @param itemId The id to lookup.
	 * @return A valid GeItem.
	 */
	public static GeItem lookup(final int itemId) {
		try {
			final URL url = new URL(HOST + GET_ID + itemId);
			final URLConnection con = url.openConnection();
			con.setReadTimeout(10000);
			final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			final StringBuilder jsonsb = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonsb.append(line);
			}
			final String json = jsonsb.toString();
			final String name = searchJSON(json, "item", "name");
			final int price = parseMultiplier(searchJSON(json, "item", "current", "price"));
			final boolean members = Boolean.parseBoolean(searchJSON(json, "item", "members"));
			final int changeToday = parseMultiplier(searchJSON(json, "item", "today", "price"));
			final double change30 = Double.parseDouble(searchJSON(json, "item", "day30", "change").replace("%", ""));
			final double change90 = Double.parseDouble(searchJSON(json, "item", "day90", "change").replace("%", ""));
			final double change180 = Double.parseDouble(searchJSON(json, "item", "day180", "change").replace("%", ""));
			final double[] changes = { change30, change90, change180 };
			final String description = searchJSON(json, "item", "description");
			final String iconUrl = searchJSON(json, "item", "icon");
			final String largeIconUrl = searchJSON(json, "item", "icon_large");
			final String type = searchJSON(json, "item", "type");
			final String typeIconUrl = searchJSON(json, "item", "typeIcon");
			return new GeItem(itemId, name, price, members, changeToday, changes,
					description, iconUrl, largeIconUrl, type, typeIconUrl);
		} catch (final Exception e) {
		}
		return null;
	}

	/**
	 * Looks up the first GeItem of a given name, returning null if failed.
	 * @param itemName The name to look up.
	 * @return A valid GeItem.
	 */
	public static GeItem lookup(String itemName) {
		try {
			itemName = itemName.toLowerCase();
			final URL url = new URL(HOST + GET_NAME + itemName.replaceAll(" ", "+"));
			final URLConnection con = url.openConnection();
			con.setReadTimeout(10000);
			final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			final StringBuilder srcsb = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null) {
				srcsb.append(line);
			}
			final String src = srcsb.substring(srcsb.indexOf("<table class=\"results\">"), srcsb.indexOf("<p id=\"res-tips\">"));
			final Pattern p = Pattern.compile(".*?/" + itemName.replaceAll(" ", "_") + "/viewitem\\.ws\\?obj=([\\d]+?)\\\">" + itemName + "</a>.*");
			final Matcher m = p.matcher(src.toLowerCase());
			if (m.find()) {
				final int id = Integer.parseInt(m.group(1));
				System.out.println(itemName+" "+id);
				return lookup(id);
			}
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * Looks up the first GeItem of a given name, returning null if failed.
	 * @param itemName The name to look up.
	 * @return A valid GeItem.
	 */
	public static int getID(String itemName) {
		try {
			itemName = itemName.toLowerCase();
			final URL url = new URL(HOST + GET_NAME + itemName.replaceAll(" ", "+"));
			final URLConnection con = url.openConnection();
			con.setReadTimeout(10000);
			final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			final StringBuilder srcsb = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null) {
				srcsb.append(line);
			}
			final String src = srcsb.substring(srcsb.indexOf("<table class=\"results\">"), srcsb.indexOf("<p id=\"res-tips\">"));
			final Pattern p = Pattern.compile(".*?/" + itemName.replaceAll(" ", "_") + "/viewitem\\.ws\\?obj=([\\d]+?)\\\">" + itemName + "</a>.*");
			final Matcher m = p.matcher(src.toLowerCase());
			if (m.find()) {
				final int id = Integer.parseInt(m.group(1));
				return id;
			}
		} catch (Exception e) {
		}
		return -1;
	}
	private static int parseMultiplier(final String str) {
		if (str.matches("-?\\d+(\\.\\d+)?[kmb]")) {
			return (int) (Double.parseDouble(str.substring(0, str.length() - 1))
					* (str.endsWith("b") ? 1000000000D : str.endsWith("m") ? 1000000
					: str.endsWith("k") ? 1000 : 1));
		} else {
			return Integer.parseInt(str);
		}
	}

	private static String searchJSON(final String json, final String...keys) {
		final String search = "\"" + keys[0] + "\":";
		int idx = json.indexOf(search) + search.length();
		if (keys.length > 1 && json.charAt(idx) == '{') {
			final String[] subKeys = new String[keys.length - 1];
			System.arraycopy(keys, 1, subKeys, 0, subKeys.length);
			return searchJSON(json.substring(idx), subKeys);
		}
		final Pattern p = Pattern.compile(".*?[,\\{]\\\"" + keys[0] + "\\\":(-?[\\d]|[\\\"\\d].*?[kmb]?[^\\\\][\\\"\\d])[,\\}].*");
		final Matcher m = p.matcher(json);
		if (m.find()) {
			String value = m.group(1);
			if (value.matches("\\\".*?\\\"")) {
				value = value.substring(1, value.length() - 1);
			}
			return value;
		}
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getName()).append("[");
		final Method[] methods = GeItem.class.getMethods();
		for (int i = 0; i < methods.length; i++) {
			final Method method = methods[i];
			final Package pack = method.getDeclaringClass().getPackage();
			if (pack == null || pack.equals(GeItem.class.getPackage())) {
				if ((method.getParameterTypes().length | method.getAnnotations().length) != 0) {
					continue;
				}
				final String methodName = method.getName();
				if (methodName.equals("getName") || methodName.equals("toString")) {
					continue;
				}
				sb.append(methodName).append("=");
				try {
					sb.append(method.invoke(this, new Object[0]));
				} catch (Exception ignored) {
				}
				sb.append(",");
			}
		}
		final String string = sb.toString();
		return string.substring(0, string.lastIndexOf(",")) + "]";
	}

}