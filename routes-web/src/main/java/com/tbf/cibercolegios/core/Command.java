package com.tbf.cibercolegios.core;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class Command<E> {

	final public void submit(List<E> errors) {
		errors.clear();

		test(errors);
		if (errors.isEmpty()) {
			execute();
		}
	}

	protected void test(List<E> errors) {
		errors.clear();
	}

	protected abstract void execute();
}