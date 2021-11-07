package se.anrop.arma3sync.cleanup.utils;

import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.RegistryException;
import com.github.sarxos.winreg.WindowsRegistry;

/**
 * Created by bjorn on 2017-02-05.
 */
public class Arma3Registry {
    private static String[] REGISTRY_PATHS = new String[] {
            "SOFTWARE\\Wow6432Node\\Bohemia Interactive Studio\\ArmA 3",
            "SOFTWARE\\Bohemia Interactive Studio\\ArmA 3",
            "SOFTWARE\\Wow6432Node\\Bohemia Interactive\\ArmA 3",
            "SOFTWARE\\Bohemia Interactive\\ArmA 3",
    };
    private static String REGISTY_VALUE_NAME = "MAIN";

    public static String getPath() {
        WindowsRegistry reg = WindowsRegistry.getInstance();
        for (String registryPath : REGISTRY_PATHS) {
            try {
                String value = reg.readString(HKey.HKLM, registryPath, REGISTY_VALUE_NAME);

                if (value != null) {
                    return value;
                }
            } catch (RegistryException e) {
                //e.printStackTrace();
            } catch (Throwable e) {
                // Support for non Windows platforms
            }
        }

        return null;
    }

}
