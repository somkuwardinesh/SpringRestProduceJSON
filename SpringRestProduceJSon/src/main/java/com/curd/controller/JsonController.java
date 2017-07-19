package com.curd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.curd.model.MyUser;
import com.curd.service.MyUserService;

@RestController
public class JsonController {

	@Autowired
	private MyUserService myUserService;

	@RequestMapping(value = "/user", method = RequestMethod.GET, headers = {
			"Accept=application/json", "Content-Type=application/json" })
	public ResponseEntity<List<MyUser>> getAllUser() {
		List<MyUser> userList = myUserService.getMyUserList();
		if (userList.isEmpty()) {
			return new ResponseEntity<List<MyUser>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<MyUser>>(userList, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, headers = {
			"Accept=application/json", "Content-Type=application/json" })
	public ResponseEntity<Void> addUser(@RequestBody MyUser myUser,
			UriComponentsBuilder builder) {
		if (myUser.getName() == null && myUser.getCity() == null
				&& myUser.getMobileNo() == 0) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} else {

			myUserService.saveMyUser(myUser);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/")
					.buildAndExpand(myUser.getId()).toUri());
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
	}

	@RequestMapping(value = "/user/{ID}", method = RequestMethod.GET, headers = "Content-Type=application/json")
	public ResponseEntity<MyUser> getUser(@PathVariable("ID") long id) {
		MyUser myUser = myUserService.findByID(id);
		if (myUser == null) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<MyUser>(myUser, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/user/{ID}", method = RequestMethod.PUT, headers = {
			"Accept=application/json", "Content-Type=application/json" })
	public ResponseEntity<MyUser> updateUser(@PathVariable("ID") long id,
			@RequestBody MyUser myUser)// @ResponseBody
	{
		MyUser user = myUserService.findByID(id);
		if (user == null) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		}

		user.setName(myUser.getName());
		user.setMobileNo(myUser.getMobileNo());
		user.setCity(myUser.getCity());
		myUserService.updateMyUser(user);
		return new ResponseEntity<MyUser>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{ID}", method = RequestMethod.DELETE, headers = {
			"Accept=application/json", "Content-Type=application/json" })
	public ResponseEntity<MyUser> deleteUser(@PathVariable("ID") long id) {
		MyUser myUser = myUserService.findByID(id);
		if (myUser == null) {
			return new ResponseEntity<MyUser>(HttpStatus.NOT_FOUND);
		}

		myUserService.deleteMyUserById(id);
		return new ResponseEntity<MyUser>(myUser, HttpStatus.OK);
	}

}
