/**
 * 
 */
package io.crs.mws.client.core.filter;

import java.util.logging.Logger;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialChip;

/**
 * A filter presenter view-ban használt filter komponensek őse.
 * <p>
 * Az osztály elsődleges feladata a gyermek osztály komponensével kiválasztott
 * érték megjelenítése vagy törlése esetén eltüntetése a megadott
 * chipPanel-n/ről a chip komponens segítségével.
 * 
 * @author robi
 *
 */
public abstract class BaseFilter extends Composite {
	private static Logger logger = Logger.getLogger(BaseFilter.class.getName());

	protected Panel chipPanel;
	protected MaterialChip chip = new MaterialChip();
	protected String chipLabel = "";
	protected boolean chipLabelIsPrefix = true;

	private Boolean chipEnabled = true;

	protected BaseFilter() {
		logger.info("BaseFilter()");
	}

	protected abstract MaterialWidget getFilterWidget();

	/**
	 * Az érték chip megjelenítésére szolgáló
	 * {@link #com.google.gwt.user.client.ui.Panel} panel megadása.
	 * 
	 * @param panel
	 */
	public void setChipPanel(Panel panel) {
		this.chipPanel = panel;
	}

	/**
	 * 
	 * @return
	 */
	public String getChipLabel() {
		return chipLabel;
	}

	/**
	 * A kiválasztott értéket leíró szöveg megadása.
	 * 
	 * @param label
	 */
	public void setChipLabel(String label) {
		chipLabel = label;
	}

	/**
	 * A kiválasott értéket leíró index karakter megadása.
	 * 
	 * @param letter
	 */
	public void setChipLetter(String letter) {
		chip.setLetter(letter);
	}

	/**
	 * A kiválasott értéket leíró index karakter színének megadása.
	 * 
	 * @param color
	 */
	public void setChipLetterColor(Color color) {
		chip.setLetterColor(color);
	}

	/**
	 * A kiválasott értéket leíró index karakter háttérszínének megadása.
	 * 
	 * @param color
	 */
	public void setChipLetterBackgroundColor(Color color) {
		chip.setLetterBackgroundColor(color);
	}

	/**
	 * A kiválasott értéket leíró ikon típusának megadása.
	 * 
	 * @param type
	 */
	public void setChipIconType(IconType type) {
		chip.setIconType(type);
		chip.getIcon().setMarginRight(0);
		chip.getIcon().setPaddingLeft(0);
		chip.setPaddingLeft(0);
	}

	/**
	 * A kiválasott értéket leíró ikon pozíciójának megadása.
	 * 
	 * @param position
	 */
	public void setChipIconPosition(IconPosition position) {
		chip.setIconPosition(position);
	}

	/**
	 * A kiválasott értéket leíró ikon színének megadása.
	 * 
	 * @param color
	 */
	public void setChipIconColor(Color color) {
		chip.setIconColor(color);
	}

	public void setChipIconPrefix(boolean prefix) {
		chip.setIconPrefix(prefix);
	}

	/**
	 * A kiválasott értéket leíró ikon méretének megadása IconSize konstanssal.
	 * 
	 * @param size
	 */
	public void setChipIconSize(IconSize size) {
		chip.setIconSize(size);
	}

	/**
	 * A kiválasott értéket leíró ikon méretének megadása standard módon.
	 * 
	 * @param size
	 * @param unit
	 */
	public void setChipIconFontSize(Double size, Unit unit) {
		chip.setIconFontSize(size, unit);
	}

	/**
	 * Az érték chip-hez kapcsolódó választó komponens grid-ben elfoglalt calle
	 * méretének beállítása.
	 * 
	 * @param grid
	 */
	public void setGrid(String grid) {
		getFilterWidget().setGrid(grid);
	}

	/**
	 * Az érték chip engedélyezése és tiltása.
	 * 
	 * @param chipEnabled
	 */
	public void setChipEnabled(Boolean chipEnabled) {
		this.chipEnabled = chipEnabled;
	}

	/**
	 * Az érték chip szövegének kiírása.
	 * <p>
	 * Ha nincs szöveg vagy üres, akkor a chip eltüntetése.
	 * 
	 * @param text
	 */
	protected void setChipText(String text) {
		if (chip.isAttached()) {
			if ((text == null) || (text.isEmpty()) || (!chipEnabled)) {
				chipPanel.remove(chip);
				return;
			}
			chip.setText(text);
		} else {
			if (!chipEnabled)
				return;
			if ((text != null) && (!text.isEmpty())) {
				chip.setText(text);
				chipPanel.add(chip);
			}
		}
	}

	public boolean isChipLabelIsPrefix() {
		return chipLabelIsPrefix;
	}

	public void setChipLabelIsPrefix(boolean chipLabelIsPrefix) {
		this.chipLabelIsPrefix = chipLabelIsPrefix;
	}

}
