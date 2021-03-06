package com.mindorks.placeholderview.compiler;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeTouch;
import com.mindorks.placeholderview.annotations.swipe.SwipingDirection;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class SwipeDirectionalViewBinderProcessor extends ViewBinderProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Layout.class)) {
            try {
                SwipeDirectionalViewBinderClassStructure
                        .create(Validator.validateLayout((TypeElement) Validator.validateTypeElement(element)),
                                getElementUtils())
                        .addConstructor()
                        .addResolveViewMethod()
                        .addRecycleViewMethod()
                        .addUnbindMethod()
                        .addBindViewPositionMethod()
                        .addBindViewMethod()
                        .addBindClickMethod()
                        .addBindLongClickMethod()
                        .addBindSwipeViewMethod()
                        .addBindSwipeInMethod()
                        .addBindSwipeOutMethod()
                        .addBindSwipeInStateMethod()
                        .addBindSwipeOutStateMethod()
                        .addBindSwipeCancelStateMethod()
                        .addBindSwipeHeadStateMethod()
                        .addBindSwipingDirectionMethod()
                        .addBindSwipeInDirectionMethod()
                        .addBindSwipeOutDirectionMethod()
                        .addBindSwipeTouchMethod()
                        .prepare()
                        .generate(getFiler());
            } catch (IOException e) {
                getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString(), element);
                return true;
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new TreeSet<>(Arrays.asList(
                SwipeInDirectional.class.getCanonicalName(),
                SwipeOutDirectional.class.getCanonicalName(),
                SwipeTouch.class.getCanonicalName(),
                SwipingDirection.class.getCanonicalName()));
        annotations.addAll(super.getSupportedAnnotationTypes());
        return annotations;
    }
}

