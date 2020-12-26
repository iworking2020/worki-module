package ru.iworking.personnel.reserve.worki.module.component.list.view.cell;

import javafx.scene.control.ListCell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.worki.module.component.list.view.controller.WorkiCandidateCellController;
import ru.iworking.personnel.reserve.worki.module.component.list.view.factory.WorkiCandidateCellControllerFactory;
import ru.iworking.personnel.reserve.worki.module.component.list.view.pane.WorkiCandidatePane;
import ru.iworking.personnel.reserve.worki.module.dto.CandidateDto;

@RequiredArgsConstructor
public class WorkiCandidateCell extends ListCell<CandidateDto> {

    private final WorkiCandidateCellControllerFactory candidateCellControllerFactory;

    @Getter private WorkiCandidatePane candidatePane;
    @Getter private WorkiCandidateCellController candidateCellController;

    @Override
    protected void updateItem(CandidateDto candidate, boolean empty) {
        super.updateItem(candidate, empty);
        if(empty || candidate == null) {
            this.candidatePane = null;
            this.candidateCellController = null;
            setText(null);
            setGraphic(null);
        } else {
            candidatePane = new WorkiCandidatePane();
            candidateCellController = candidateCellControllerFactory.create(candidatePane);
            candidateCellController.setData(candidate);
            setText(null);
            setGraphic(candidatePane);
        }
    }



}
