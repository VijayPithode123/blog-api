package com.blogapis.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="post")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {

	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private String title;
		private String content;
		private String imageName;
		private Date date;
		
		@ManyToOne
		private Category category;
	
		@ManyToOne
		private User user;
		
		@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
		private Set<Comment> set=new HashSet();
		
		
		
}
