package valorless.havenbags;

import valorless.valorlessutils.config.Config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

import valorless.valorlessutils.ValorlessUtils.Log;
import valorless.valorlessutils.ValorlessUtils.Utils;

public class Lang {
	
	public static Config lang;
		
	public static class Placeholders{
		public static String plugin = "§7[§aHaven§bBags§7]§r";
	}
	
	public static String Parse(String text) {
		if(!Utils.IsStringNullOrEmpty(text)) {
			text = hex(text);
			text = text.replace("&", "§");
			text = text.replace("\\n", "\n");
		}
		return text;
	}

	public static String Get(String key) {
		if(lang.Get(key) == null) {
			Log.Error(HavenBags.plugin, String.format("Lang.yml is missing the key '%s'!", key));
			return "§4error";
		}
		return Parse(lang.GetString(key));
	}
	
	public static String Get(String key, Object arg) {
		if(lang.Get(key) == null) {
			Log.Error(HavenBags.plugin, String.format("Lang.yml is missing the key '%s'!", key));
			return "§4error";
		}
		return Parse(String.format(lang.GetString(key), arg.toString()));
	}
	
	public static String Get(String key, Object arg1, Object arg2) {
		if(lang.Get(key) == null) {
			Log.Error(HavenBags.plugin, String.format("Lang.yml is missing the key '%s'!", key));
			return "§4error";
		}
		return Parse(String.format(lang.GetString(key), arg1.toString(), arg2.toString()));
	}
	
	public static String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
           
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }
           
            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
