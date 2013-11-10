package org.jsondoc.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiStatus;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.sample.pojo.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(name = "User Services", description = "Methods for managing users")
@Controller
@RequestMapping(value = "/users")
public class UserController {

	@ApiMethod(path = "/users/{id}", status = ApiStatus.PROPOSED, verb = ApiVerb.GET, description = "Gets a user with the given ID", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiErrors(apierrors = { @ApiError(code = "3000", description = "User not found"), @ApiError(code = "9000", description = "Illegal argument") })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	User user(@PathVariable @ApiParam(name = "id", description = "The user's ID", required = true) Integer id) {
		return new User(id, "jsondoc-user", 30, "M");
	}

	@ApiMethod(path = "/users/{gender}/{age}", status = ApiStatus.UNDER_DEVELOPMENT, verb = ApiVerb.GET, description = "Gets users with the given gender and age", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiErrors(apierrors = { @ApiError(code = "3000", description = "User not found"), @ApiError(code = "9000", description = "Illegal argument") })
	@RequestMapping(value = "/{gender}/{age}", method = RequestMethod.GET)
	public @ResponseBody @ApiResponseObject
	List<User> userAge(@PathVariable @ApiParam(name = "gender", description = "The user's gender", required = true) String gender, @PathVariable @ApiParam(name = "age", description = "The user's required age", required = true) Integer age) {
		List<User> users = new ArrayList<User>();
		users.add(new User(1, "jsondoc-user-1", age, gender));
		users.add(new User(2, "jsondoc-user-2", age, gender));
		return users;
	}

    @ApiMethod(path = "/users/q/{name}/{gender}?agemin={agemin}&agemax={agemax}", status = ApiStatus.RELEASED, verb = ApiVerb.GET, description = "Gets a user with the given gender and given age", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiErrors(apierrors = { @ApiError(code = "3000", description = "User not found"), @ApiError(code = "9000", description = "Illegal argument") })
    @RequestMapping(value = "/q/{name}/{gender}", method = RequestMethod.GET)
    public @ResponseBody @ApiResponseObject
    List<User> usersByNameAndGenderAndAge(@PathVariable("name") @ApiParam(name = "name", description = "The user's name", required = true, paramType= ApiParamType.PATH) String name, @PathVariable("gender") @ApiParam(name = "gender", description = "The user's gender", required = true, paramType=ApiParamType.PATH) String gender, @RequestParam(value="agemin") @ApiParam(name = "agemin", description = "The user's min age", required = false, paramType=ApiParamType.QUERY) Integer agemin, @RequestParam(value="agemax") @ApiParam(name = "agemax", description = "The user's max age", required = false, paramType=ApiParamType.QUERY) Integer agemax) {
          List<User> users = new ArrayList<User>();
          users.add(new User(1, "jsondoc-user-1", agemin, gender));
          users.add(new User(2, "jsondoc-user-2", agemax, gender));
          return users;
    }
}
