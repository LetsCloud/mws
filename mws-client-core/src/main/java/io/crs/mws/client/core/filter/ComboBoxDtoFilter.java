/**
 * 
 */
package io.crs.mws.client.core.filter;

import java.util.ArrayList;
import java.util.List;

import io.crs.mws.shared.dto.BaseDto;

/**
 * @author robi
 *
 */
public abstract class ComboBoxDtoFilter<T extends BaseDto> extends ComboBoxFilter<T> {


	/**
	 * A ComboBox-val kiválasztott elemek egyedi kulcsainak visszaadása.
	 * 
	 * @return
	 */
	public List<String> getSelectedKeys() {
		List<String> result = new ArrayList<String>();
		for (T dto : comboBox.getSelectedValue())
			result.add(dto.getWebSafeKey());
		return result;
	}

	/**
	 * A ComboBox-val kiválasztott elem egyedi kulcsának visszaadása.
	 * 
	 * @return
	 */
	public String getSelectedKey() {
		if (comboBox.getSelectedValue().isEmpty())
			return null;
		return comboBox.getSelectedValue().get(0).getWebSafeKey();
	}

	public void setSelectedKey(String webSafeKey) {
		List<T> values = comboBox.getValues();
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i).getWebSafeKey().equals(webSafeKey)) {
				comboBox.setSelectedIndex(i);
				setChipText(createChipText(comboBox.getSelectedValue()));
				return;
			}
		}
	}

}
