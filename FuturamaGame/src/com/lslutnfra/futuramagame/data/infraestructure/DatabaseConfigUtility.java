package com.lslutnfra.futuramagame.data.infraestructure;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.lslutnfra.futuramagame.domain.entity.Score;

public class DatabaseConfigUtility extends OrmLiteConfigUtil {

	private static final Class<?>[] classes = new Class[]
			{ 
				Score.class
			};
	
	public static void main(String[] args) throws SQLException, IOException {
		writeConfigFile("ormlite_config.txt", classes);
	}
}
