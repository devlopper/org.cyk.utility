package org.cyk.utility.client.controller.web.jsf.primefaces.tag;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.primefaces.model.TreeNode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Dependent
@Getter @Setter @NoArgsConstructor
public class InputTree implements Serializable {
	private static final long serialVersionUID = 1L;

	private TreeNode root;
	private TreeNode[] selected;
	
	public InputTree(TreeNode root) {
		this.root = root;
	}
	
}
