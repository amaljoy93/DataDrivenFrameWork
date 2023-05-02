package com.datadriven.framework.utils;

import java.util.Date;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class DateUtils {
	
	public static String getTimeStamp(){
     Date date = new Date();
    return date.toString().replaceAll(":", "_").replace(" ", "_");
}
}