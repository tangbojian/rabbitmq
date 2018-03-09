package com.tbj.rabbitmq.validation;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Student {

	@NotNull(message = "��������ΪNULL")
	@NotBlank(message = "��������Ϊ��")
	private String name;

	@NotNull(message = "ѧУ����ΪNULL")
	@NotBlank(message = "ѧУ����Ϊ��")
	private String school;

	@Min(value = 0, message = "����С��0")
	private BigDecimal money;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public static void main(String[] args) {

		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();

		Student student = new Student();
		student.setName("");
		student.setSchool("");
		student.setMoney(new BigDecimal(-1));

		Set<ConstraintViolation<Student>> errors = validator.validate(student);
		Iterator<ConstraintViolation<Student>> iterator = errors.iterator();
		while (iterator.hasNext()) {
			ConstraintViolation<Student> constraintViolation = iterator.next();
			System.out.println(constraintViolation.getMessage());
		}

	}

}
