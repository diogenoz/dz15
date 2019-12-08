package com.gb.dz15.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task implements Serializable {
    @Id
    @SequenceGenerator(name = "seqTask", sequenceName = "s_task")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTask")
    @Column(name = "id")
    public Long id;
    @Column(name = "name")
    @NotNull
    public String name;
    @ManyToOne
    @JoinColumn(name = "owner")
    public Employee owner;
    @ManyToOne
    @JoinColumn(name = "assignee")
    public Employee assignee;
    @Column(name = "description")
    public String description;
    @Enumerated
    public TaskStatus status;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof Task)) {
            return false;
        }
        Task otherTask = (Task) obj;
        return this.id.equals(otherTask.id) || this.name.equals(otherTask.name);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", this.id, this.name, this.owner, this.assignee, this.status.getStatusName());
    }

    public enum TaskStatus {
        Open("Open", 1), InProgress("In Progress", 2), Done("Done", 3);
        private String statusName;
        private int prior;

        TaskStatus(String statusName, int prior) {
            this.statusName = statusName;
            this.prior = prior;
        }

        public String getStatusName() {
            return statusName;
        }
    }
}
