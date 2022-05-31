package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class DemoApplication {


	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		jFrame.setSize(500,500);
		jFrame.getContentPane().setLayout(null);

		JButton startButton =  new JButton("start server");
		JButton stopButton =  new JButton("stop server");

		startButton.setBounds(0,0,200,100);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				go(args);
			}
		});

		stopButton.setBounds(200,0,200,100);

		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
				stop(args);
			}
		});

		jFrame.getContentPane().add(startButton);
		jFrame.getContentPane().add(stopButton);


		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		jFrame.setVisible(true);


		//go(args);
	}
	private static void go(String[] args) {
		ctx = SpringApplication.run(DemoApplication.class, args);
		System.out.println("starting the server");
	}
	private static void stop(String[] args) {
		ctx.close();
		System.out.println("closing the server");
	}



}
