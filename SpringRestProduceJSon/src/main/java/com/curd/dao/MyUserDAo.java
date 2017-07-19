package com.curd.dao;

import java.util.List;

import com.curd.model.MyUser;

public interface MyUserDAo {

	public void saveMyUser(MyUser myUser);
	
	public void updateMyUser(MyUser myUser);
	
	
	public List<MyUser> getMyUserList();

	public MyUser findByID(long id);
	
	public MyUser findByName(String name);
	
	
	public void deleteMyUserById(long id);	
	
	
}
