package org.cause4code.jackson.jsonview.dto;

import java.io.Serializable;
import java.util.Calendar;

import lombok.*;
import org.cause4code.jackson.jsonview.view.View;

import com.fasterxml.jackson.annotation.JsonView;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {

	@JsonView(value = { View.UserView.External.class })
	private Integer id;

	@JsonView(value = { View.UserView.External.class })
	private String firstName;

	@JsonView(value = { View.UserView.External.class })
	private String lastName;

	@JsonView(value = { View.UserView.Internal.class })
	private String ssn;

	@JsonView(value = { View.UserView.Internal.class })
	private Calendar dob;

	@JsonView(value = { View.UserView.Internal.class })
	private String mobileNo;
}
