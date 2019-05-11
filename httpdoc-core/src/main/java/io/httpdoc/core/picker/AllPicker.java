package io.httpdoc.core.picker;

import java.util.Collection;

/**
 * 与门逻辑混合挑选器
 *
 * @author Payne 646742615@qq.com
 * 2019/5/11 11:32
 */
public class AllPicker extends MixPicker {

    public AllPicker() {
    }

    public AllPicker(Collection<? extends Picker> pickers) {
        super(pickers);
    }

    @Override
    public MixPicker mix(Picker picker) {
        add(picker);
        return this;
    }

    @Override
    public boolean pick(PickContext pickContext) {
        Picker[] pickers = this.pickers.toArray(new Picker[0]);
        for (Picker picker : pickers) {
            if (!picker.pick(pickContext)) {
                return false;
            }
        }
        return true;
    }
}
