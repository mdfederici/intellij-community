// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package git4idea.actions;

import git4idea.repo.GitRepository;
import org.jetbrains.annotations.NotNull;

public interface GitOngoingOperationAction {
  boolean isEnabled(@NotNull GitRepository repository);

  void performInBackground(@NotNull GitRepository repository);
}
