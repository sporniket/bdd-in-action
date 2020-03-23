package com.sporniket.littlecauldron.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marqueur pour identifier les méthodes dont on veut tracer l'entrée et la sortie.
 *
 * @author spornda
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogEntryAndExit
{

}
