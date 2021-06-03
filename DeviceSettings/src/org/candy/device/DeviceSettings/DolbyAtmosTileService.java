/*
* Copyright (C) 2018 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.candy.device.DeviceSettings;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import androidx.preference.PreferenceManager;

import org.candy.device.DeviceSettings.DolbySwitch;

@TargetApi(24)
public class DolbyAtmosTileService extends TileService {
    private boolean enabled = false;
    final DolbySwitch mDolbySwitch;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        enabled = mDolbySwitch.isCurrentlyEnabled();
        if (!enabled) {
            getQsTile().setState(Tile.STATE_UNAVAILABLE);
        } else {
            getQsTile().setIcon(Icon.createWithResource(this,
                    enabled ? R.drawable.ic_dolby_atmos : R.drawable.ic_dolby));
            getQsTile().setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        }
        getQsTile().updateTile();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        enabled = mDolbySwitch.isCurrentlyEnabled();
        if (enabled) {
            Intent mDolbyIntent = new Intent(this,
                    com.oneplus.sound.tuner.panoramic.DolbyPanoramicSoundActivity.class);
            mDolbyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityAndCollapse(mDolbyIntent);
            //this.startService(mDolbyIntent);
        } else {
            return;
        }
    }
}
