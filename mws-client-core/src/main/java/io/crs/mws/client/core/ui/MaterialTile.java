/**
 * 
 */
package io.crs.mws.client.core.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.HandlerRegistration;
import gwt.material.design.client.base.HasAxis;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.mixin.CssNameMixin;
import gwt.material.design.client.constants.Axis;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.js.Window;

/**
 * @author CR
 *
 */
public class MaterialTile extends MaterialWidget implements HasAxis {

    private final CssNameMixin<MaterialTile, Axis> axisMixin = new CssNameMixin<>(this);
    private boolean detectOrientation = false;
    protected HandlerRegistration orientationHandler;

    /**
     * Creates an empty card.
     */
    public MaterialTile() {
        super(Document.get().createDivElement(), "tile");
    }

    @Override
    public void setGrid(String grid) {
        super.setGrid(grid);
        addStyleName(CssName.NO_PADDING);
    }

    @Override
    public void setAxis(Axis axis) {
        axisMixin.setCssName(axis);
    }

    @Override
    public Axis getAxis() {
        return axisMixin.getCssName();
    }

    public void setDetectOrientation(boolean detectOrientation) {
        this.detectOrientation = detectOrientation;

        if(orientationHandler != null) {
            orientationHandler.removeHandler();
            orientationHandler = null;
        }

        if(detectOrientation) {
            orientationHandler = com.google.gwt.user.client.Window.addResizeHandler(resizeEvent -> detectAndApplyOrientation());
            detectAndApplyOrientation();
        }
    }

    protected void detectAndApplyOrientation() {
        if (Window.matchMedia("(orientation: portrait)")) {
            setAxis(Axis.VERTICAL);
        } else {
            setAxis(Axis.HORIZONTAL);
        }
    }

    public boolean isDetectOrientation() {
        return detectOrientation;
    }
}