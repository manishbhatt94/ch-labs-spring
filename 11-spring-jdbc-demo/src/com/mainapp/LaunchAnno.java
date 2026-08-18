package com.mainapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.annoConf.AppConfig;
import com.annoConf.DataConfig;
import com.crud.EmployeeCrudAnno;

public class LaunchAnno {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataConfig.class,
				AppConfig.class);

		EmployeeCrudAnno crud = ctx.getBean("employeeCrudBean", EmployeeCrudAnno.class);

		crud.insertEmployee("Method Man", "Park Hill Project, Staten Island, New York State", 23000);
		crud.insertEmployee("Ol' Dirty Bastard", "Park Hill Project, Staten Island, New York State", 31000);

		ctx.close();

	}

}
