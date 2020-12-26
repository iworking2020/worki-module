package ru.iworking.personnel.reserve.worki.module.component.list.view.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.worki.module.component.list.view.controller.WorkiCandidateCellController;
import ru.iworking.personnel.reserve.worki.module.component.list.view.pane.WorkiCandidatePane;

@Component
@Lazy
@RequiredArgsConstructor
public class WorkiCandidateCellControllerFactory {

    public WorkiCandidateCellController create(WorkiCandidatePane candidatePane) {
        return new WorkiCandidateCellController(candidatePane);
    }

}
