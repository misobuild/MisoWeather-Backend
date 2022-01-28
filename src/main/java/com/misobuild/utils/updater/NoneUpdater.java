package com.misobuild.utils.updater;

import com.misobuild.domain.region.Region;
import org.springframework.stereotype.Component;

@Component
public class NoneUpdater implements Updater{
    @Override
    public void update(Region region) {
    }
}
