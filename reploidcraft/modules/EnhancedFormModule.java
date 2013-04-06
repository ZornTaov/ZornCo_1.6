package zornco.reploidcraft.modules;

import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;

public class EnhancedFormModule extends PowerModuleBase {
	public static final String ENHANCED_FORM = "Enhanced Form";
	
	public EnhancedFormModule(List<IModularItem> validItems) {
		super(validItems);
	}
	
	@Override
	public String getTextureFile() {
		return "greendrone";
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_COSMETIC;
	}

	@Override
	public String getName() {
		return ENHANCED_FORM;
	}

	@Override
	public String getDescription() {
		return "An enhanced form of the armor or buster, enhancing the specialized modules for this part.";
	}
}
