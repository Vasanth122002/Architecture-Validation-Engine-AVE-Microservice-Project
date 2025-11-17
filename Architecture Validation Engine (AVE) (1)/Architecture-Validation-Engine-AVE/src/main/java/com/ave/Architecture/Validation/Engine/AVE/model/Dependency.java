package com.ave.Architecture.Validation.Engine.AVE.model;
import jakarta.persistence.*;

@Entity
@Table(name = "dependencies")
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Service requester;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Service provider;

    public Dependency() {
    }

    public Dependency( Service requester, Service provider) {

        this.requester = requester;
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service getRequester() {
        return requester;
    }

    public void setRequester(Service requester) {
        this.requester = requester;
    }

    public Service getProvider() {
        return provider;
    }

    public void setProvider(Service provider) {
        this.provider = provider;
    }

}
